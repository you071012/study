package com.ukar.study.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig {

    @Value("${redis.address:127.0.0.1}")
    private String redisAddress;

    @Value("${redis.port:6379}")
    private int redisPort;

    @Value("${redis.password:123456}")
    private String password;

    /**
     * type:auto,cluster,sentinel,jedispool
     * auto select alicom to jedispool
     */
    @Value("${redis.connection.type:auto}")
    private String redisConnectionType;

    @Value("${redis.sentinel.transact:}")
    private String redisSentinelMaster;

    @Value("${redis.timeout:500}")
    private int timeout;

    @Value("${redis.maxRedirects:5}")
    private int maxRedirects;

    @Value("${redis.minIdle:50}")
    private int minIdle;

    @Value("${redis.maxIdle:50}")
    private int maxIdle;

    @Value("${redis.maxTotal:1500}")
    private int maxTotal;

    @Value("${redis.maxWaitMillis:500}")
    private int maxWaitMillis;

    @Value("${redis.ssl:false}")
    private boolean ssl;

    @Bean(value = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean(value = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(@Qualifier("redisConnectionFactory")
                                                           RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        setSerializer(template);
        return template;
    }

    @Bean(value = "redisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory jedisConnectionFactory;
        if ("auto".equalsIgnoreCase(redisConnectionType)) {
            if (redisAddress.contains("redis.rds.aliyuncs.com") && StringUtils.isNotBlank(String.valueOf(redisPort))) {
                jedisConnectionFactory = new JedisConnectionFactory();
                jedisConnectionFactory.setHostName(redisAddress);
                jedisConnectionFactory.setPort(redisPort);
            } else if(StringUtils.isNotBlank(redisSentinelMaster) && redisAddress.contains(":")) {
                jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration());
            } else if(redisAddress.contains(":") && StringUtils.isBlank(redisSentinelMaster)) {
                jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration());
            } else {
                jedisConnectionFactory = new JedisConnectionFactory();
                jedisConnectionFactory.setHostName(redisAddress);
                jedisConnectionFactory.setPort(redisPort);
            }
        } else if ("cluster".equalsIgnoreCase(redisConnectionType)) {
            jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration());
        } else if ("sentinel".equalsIgnoreCase(redisConnectionType)) {
            jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration());
        } else if ("jedispool".equalsIgnoreCase(redisConnectionType)) {
            jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName(redisAddress);
            jedisConnectionFactory.setPort(redisPort);
        } else {
            jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName(redisAddress);
            jedisConnectionFactory.setPort(redisPort);
        }

        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setTimeout(timeout);
        jedisConnectionFactory.setPassword(password);
        if (ssl) {
            jedisConnectionFactory.setUseSsl(ssl);
        }

        return jedisConnectionFactory;
    }

    private RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setClusterNodes(getRedisTemplateAddress(redisAddress));
        redisClusterConfiguration.setMaxRedirects(maxRedirects);
        return redisClusterConfiguration;
    }

    private RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration config = new RedisSentinelConfiguration();
        config.master(redisSentinelMaster);
        config.setSentinels(getRedisTemplateAddress(redisAddress));
        return config;
    }


    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return jedisPoolConfig;
    }

    private Set<RedisNode> getRedisTemplateAddress(String s) {
        Set<RedisNode> nodes = new HashSet<>();
        for (String hoststuff : s.split("(?:\\s|,)+")) {
            if ("".equals(hoststuff)) {
                continue;
            }

            int finalColon = hoststuff.lastIndexOf(':');
            if (finalColon < 1) {
                throw new IllegalArgumentException("Invalid server ``" + hoststuff
                        + "'' in list:  " + s);
            }
            String hostPart = hoststuff.substring(0, finalColon);
            String portNum = hoststuff.substring(finalColon + 1);
            nodes.add(new RedisNode(hostPart, Integer.parseInt(portNum)));
        }
        return nodes;
    }

    /**
     * redis缓存对象序列化接口
     *
     * @param template
     */
    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
    }
}
