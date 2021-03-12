package com.ukar.study.bucket;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class NumBucket implements Bucket{

    // 默认最大桶令牌个数
    private static final int DEFAULT_BUCKET_SIZE = 100000000;

    // 默认平均生成令牌数
    private static final int DEFAULT_AVG_FLOW_RATE = 1000;

    // 桶最大令牌数
    private int maxFlowRate;

    // 桶平均生成令牌数
    private int avgFlowRate;

    private static final char A_CHAR = 'a';

    private final ReentrantLock lock = new ReentrantLock(true);

    // 队列来缓存桶数量：最大的流量峰值就是 = DEFAULT_BUCKET_SIZE 64M = 1 * 1024 * 1024 * 64
    private ArrayBlockingQueue<String> tokenQueue = new ArrayBlockingQueue<String>(DEFAULT_BUCKET_SIZE);

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public NumBucket() {
    }

    public NumBucket(int maxFlowRate, int avgFlowRate) {
        this.maxFlowRate = maxFlowRate;
        this.avgFlowRate = avgFlowRate;
        if(maxFlowRate != 0){
            tokenQueue = new ArrayBlockingQueue<String>(maxFlowRate);
        }
    }

    @Override
    public boolean getToken(int needTokenNum) {
        lock.lock();
        try {
            boolean result = needTokenNum <= tokenQueue.size(); // 是否存在足够的桶数量
            if (!result) {
                return false;
            }

            int tokenCount = 0;
            for (int i = 0; i < needTokenNum; i++) {
                String poll = tokenQueue.poll();
                if (poll != null) {
                    tokenCount++;
                }
            }

            return tokenCount == needTokenNum;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void init() {
        log.info("字节令牌桶准备初始化，平均生成令牌数：{}，最大令牌数：{}", avgFlowRate, maxFlowRate);
        // 初始化令牌生产者
        NumBucket.NumTokenProducer tokenProducer = new NumBucket.NumTokenProducer(avgFlowRate, this);
        //以固定频率执行，执行线程 初始化延时时间 频率时间间隔 时间单位
        scheduledExecutorService.scheduleAtFixedRate(tokenProducer, 0, 1,
                TimeUnit.SECONDS);
    }

    @Override
    public void destory() {
        scheduledExecutorService.shutdown();
    }

    private void addTokens(Integer num) {
        log.info("开始添加字节桶令牌，当前容量：{}", tokenQueue.size());
        // 若是桶已经满了，就不再家如新的令牌
        for (int i = 0; i < num; i++) {
            boolean offer = tokenQueue.offer(String.valueOf(A_CHAR));
            if(!offer){
                break;
            }
        }
    }

    class NumTokenProducer implements Runnable {

        private int avgFlowRate;
        private NumBucket numBucket;

        public NumTokenProducer(int avgFlowRate, NumBucket numBucket) {
            this.avgFlowRate = avgFlowRate;
            this.numBucket = numBucket;
        }

        @Override
        public void run() {
            numBucket.addTokens(avgFlowRate);
        }
    }
}