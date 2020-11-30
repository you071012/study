package com.ukar.study.jdk.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列学习
 * java延迟队列提供了在指定时间才能获取队列元素的功能，队列头元素是最接近过期的元素。
 * 每次take或poll都只会返回已经超过过期时间最大的那个元素
 * 没有过期元素的话，使用poll()方法会返回null值，超时判定是通过getDelay(TimeUnit.MILLISECONDS)方法的返回值小于等于0来判断。
 * 但是因为计算可能会耗时毫秒级别，当使用getDelay(TimeUnit.MILLISECONDS)可能会返回几毫秒的细微差别，
 * 当使用秒级别的延时队列，可以忽略不计
 * 延时队列不能存放空元素
 */
public class DelayQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<DelayTask> queue = new DelayQueue<>();
        queue.offer(new DelayTask("任务1", 5000));
        queue.offer(new DelayTask("任务2", 2000));
        queue.offer(new DelayTask("任务3", 9000));
        queue.offer(new DelayTask("任务4", 7000));
        queue.offer(new DelayTask("任务5", 2000));
        queue.offer(new DelayTask("任务6", 7000));

        System.out.println("开始执行前时间：" + System.currentTimeMillis());
        System.out.println("当前队列大小：" + queue.size());

        for(int i = 0; i< 6; i++) {
            DelayTask take = queue.take();
            System.out.println(System.currentTimeMillis());
            System.out.println(take.getName() + ":" + take.getDelay(TimeUnit.MILLISECONDS));
        }

        DelayTask take = queue.poll();
        System.out.println(take);
    }
}
