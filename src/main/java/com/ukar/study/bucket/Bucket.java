package com.ukar.study.bucket;

/**
 * 令牌桶基类
 */
public interface Bucket {

    /**
     * 获取令牌
     * @param needTokenNum
     * @return
     */
    boolean getToken(int needTokenNum);

    void init();

    void destory();
}
