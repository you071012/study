package com.ukar.study.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class RedisLock {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisService redisService;

    private static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }


    public boolean tryLock(Lock lock, long expire){
        int times = 3;
        do {
            try{
                return lock(lock, expire);
            }catch (Exception e){
                log.info("redis加锁异常，开始第{}次重试", times);
            }
            times--;
        }while (times > 0);

        return false;
    }

    /**
     * 正确加锁模式
     * @param lock
     * @param expire 过期时间，单位毫秒。EX：秒，PX：毫秒
     * @return
     */
    public boolean lock(Lock lock, long expire) {
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.set(lock.getLockKey(), lock.getLockVal(), "NX", "PX", expire);
            };
            String result = redisTemplate.execute(callback);

            return !StringUtils.isEmpty(result);
        } catch (Exception e) {
            log.error("set redis occured an exception", e);
            throw e;
        }
    }

    /**
     * 错误加锁模式1
     * 当redis服务挂了，设置过期时间失效，该key将一直不会过期
     * @param key
     * @param expire
     * @return
     */
    @Deprecated
    public boolean lock2(String key, long expire) {
        try {
            String expireTime = String.valueOf(System.currentTimeMillis() + expire);
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, expireTime);
            if(aBoolean){
                return redisService.set(key, expireTime, expire);
            }
        }catch (Exception e){
            log.error("set redis occured an exception", e);
        }
        return false;
    }

    /**
     * 错误加锁模式2
     * 该加锁模式可以解决setNx无法设置过期时间问题，当redis服务挂了，下次加锁只要锁时间已过期还是可以继续加锁，
     * 但是存在一个并发问题，导致锁内容被串改。见FIXME处注释，当多台机器并发情况下value值可能被串改，除非所有服务器时间一致
     * @param key
     * @param expire
     * @return
     */
    @Deprecated
    public boolean lock3(String key, long expire) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            String expireTime = String.valueOf(currentTimeMillis + expire);
            Boolean acquire = redisTemplate.opsForValue().setIfAbsent(key, expireTime);
            if(acquire){
                return true;
            }

            // 如果锁存在，获取锁的过期时间
            String currentValueStr = (String) redisTemplate.opsForValue().get(key);
            if (Objects.nonNull(currentValueStr) && Long.parseLong(currentValueStr) < currentTimeMillis) {
                // FIXME 此处值可能会串改
                String oldValueStr = (String) redisTemplate.opsForValue().getAndSet(key, expireTime);
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    // 考虑多线程并发的情况，只有一个线程的设置值和当前值相同，它才有权利加锁
                    return true;
                }
            }
        }catch (Exception e){
            log.error("set redis occured an exception", e);
        }
       return false;
    }


    public String getLockVal(String key) {
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.get(key);
            };
            String result = redisTemplate.execute(callback);
            return result;
        } catch (Exception e) {
            log.error("get redis occured an exception", e);
        }
        return "";
    }

    public boolean delLockVal(String key) {
        try {
            RedisCallback<Long> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.del(key);
            };
            Long result = redisTemplate.execute(callback);
            return result != null && result > 0;
        } catch (Exception e) {
            log.error("get redis occured an exception", e);
        }
        return false;
    }

    public boolean tryRelease(Lock lock){
        int times = 3;
        do {
            try{
                return releaseLock(lock);
            }catch (Exception e){
                log.info("redis释放异常，开始第{}次重试", times);
            }
            times--;
        }while (times > 0);

        return false;
    }

    /**
     * 释放锁，为了防止锁内容被修改，可以在释放锁时比较lock中val值是否一样，这里就不做比较了
     * @param lock
     * @return
     */
    public boolean releaseLock(Lock lock) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            List<String> keys = new ArrayList<>();
            keys.add(lock.getLockKey());
            List<String> args = new ArrayList<>();
            args.add(lock.getLockVal());

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            RedisCallback<Long> callback = (connection) -> {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            };
            Long result = redisTemplate.execute(callback);
            return result != null && result > 0;
        } catch (Exception e) {
            log.error("release lock occured an exception", e);
            throw e;
        }
    }

    /**
     * 对lock中val加一并返回，如果key不存在直接设置并返回
     * @param lock
     * @return
     */
    public Lock incrAndGet(Lock lock) {
        boolean f = false;
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.set(lock.getLockKey(), lock.getLockVal(), "NX");
            };
            String result = redisTemplate.execute(callback);
            if(StringUtils.equals("OK", result)){
                f = true;
            }
        } catch (Exception e) {
           log.error("redis异常",e);
           throw e;
        }
        if(f){
            return lock;
        }else {
            RedisCallback<Long> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                Long incr = commands.incrBy(lock.getLockKey(),1);
                return incr;
            };
            Long newVal = redisTemplate.execute(callback);
            lock.setLockVal(String.valueOf(newVal));
            return lock;
        }
    }
}
