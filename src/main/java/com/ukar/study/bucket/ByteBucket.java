package com.ukar.study.bucket;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 按照字节获取令牌
 */
@Slf4j
public class ByteBucket implements Bucket{

    // 默认桶令牌个数 即最大瞬间流量是64M
    private static final int DEFAULT_BUCKET_SIZE = 1024 * 1024 * 64;

    // 默认平均生成令牌数 1024 字节，1kb
    private static final int DEFAULT_AVG_FLOW_RATE = 1024;

    // 桶最大令牌数
    private int maxFlowRate;

    // 桶平均生成令牌数
    private int avgFlowRate;

    private static final byte A_CHAR = 'a';

    private final ReentrantLock lock = new ReentrantLock(true);

    // 队列来缓存桶数量：最大的流量峰值就是 = DEFAULT_BUCKET_SIZE 64M = 1 * 1024 * 1024 * 64
    private ArrayBlockingQueue<Byte> tokenQueue = new ArrayBlockingQueue<Byte>(DEFAULT_BUCKET_SIZE);

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public ByteBucket() {
        this.avgFlowRate = DEFAULT_AVG_FLOW_RATE;
        this.maxFlowRate = DEFAULT_BUCKET_SIZE;
    }

    public ByteBucket(int maxFlowRate, int avgFlowRate) {
        this.maxFlowRate = maxFlowRate;
        this.avgFlowRate = avgFlowRate;
        if(maxFlowRate != 0){
            tokenQueue = new ArrayBlockingQueue<Byte>(maxFlowRate);
        }
    }

    @Override
    public void init(){
        log.info("字节令牌桶准备初始化，平均生成令牌数：{}，最大令牌数：{}", avgFlowRate, maxFlowRate);
        // 初始化令牌生产者
        ByteTokenProducer tokenProducer = new ByteTokenProducer(avgFlowRate, this);
        //以固定频率执行，执行线程 初始化延时时间 频率时间间隔 时间单位
        scheduledExecutorService.scheduleAtFixedRate(tokenProducer, 0, 1,
                TimeUnit.SECONDS);
    }

    @Override
    public void destory(){
        scheduledExecutorService.shutdown();
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
                Byte poll = tokenQueue.poll();
                if (poll != null) {
                    tokenCount++;
                }
            }

            return tokenCount == needTokenNum;
        } finally {
            lock.unlock();
        }
    }

    private void addTokens(Integer num) {
        log.info("开始添加字节桶令牌，当前容量：{}", tokenQueue.size());
        // 若是桶已经满了，就不再家如新的令牌
        for (int i = 0; i < num; i++) {
            boolean offer = tokenQueue.offer(Byte.valueOf(A_CHAR));
            if(!offer){
                break;
            }
        }
    }

    class ByteTokenProducer implements Runnable {

        private int avgFlowRate;
        private ByteBucket byteBucket;

        public ByteTokenProducer(int avgFlowRate, ByteBucket byteBucket) {
            this.avgFlowRate = avgFlowRate;
            this.byteBucket = byteBucket;
        }

        @Override
        public void run() {
            byteBucket.addTokens(avgFlowRate);
        }
    }
}
