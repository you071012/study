package com.ukar.study.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/04/20/18:57
 * @Description: 无论是同实例还是非同实例，只要是使用同一个锁的方法，都会阻塞。
 * 在方法上加 synchronized 相当于使用this锁，不同实例等于使用了不同的锁
 */
@Slf4j
public class SynchronizedLockDemo {

    public static final byte[] lock = {};

    public void funA() throws Exception{
        synchronized (lock){
            Thread.sleep(2000);
            log.info("funA......");
        }
    }

    public void funB() throws Exception{
        synchronized (lock){
            log.info("funB......" );
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                new SynchronizedLockDemo().funA();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                new SynchronizedLockDemo().funB();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        Thread.currentThread().join();
    }
}
