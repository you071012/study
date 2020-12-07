package com.ukar.study.redis;

import com.ukar.study.StudyApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StudyApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisLock redisLock;

    @Test
    public void test(){
        redisService.set("test", "aaa");
        System.out.println(redisService.get("test"));
    }

    @Test
    public void testLock() throws InterruptedException {
        Lock lock = new Lock();
        lock.setLockKey("lock1");
        lock.setLockVal("123456");

        boolean lockResult = redisLock.lock(lock, 2000);
        Thread.sleep(1900);
        boolean lockResult2 = redisLock.lock(lock, 2000);
        System.out.println(lockResult);
        System.out.println(lockResult2);

    }

    @Test
    public void testLock2() throws InterruptedException {
        String key = "lock2";
        redisService.remove(key);

        boolean lockResult = redisLock.lock2(key, 2000);
        System.out.println(redisService.get(key));
        Thread.sleep(3000);
        boolean lockResult2 = redisLock.lock2(key, 2000);
        System.out.println(redisService.get(key));
        System.out.println(lockResult);
        System.out.println(lockResult2);


    }

    @Test
    public void testLock3() throws InterruptedException {
        String key = "lock3";

        boolean lockResult = redisLock.lock3(key, 2000);
        System.out.println(redisService.get(key));
        Thread.sleep(1900);
        boolean lockResult2 = redisLock.lock3(key, 2000);
        System.out.println(redisService.get(key));
        System.out.println(lockResult);
        System.out.println(lockResult2);


    }
}
