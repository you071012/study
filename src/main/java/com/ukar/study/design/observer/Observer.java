package com.ukar.study.design.observer;

/**
 * @author jia.you
 * @date 2020/04/17
 *
 * 观察者模式，观察者接口定义
 */
public interface Observer {

    /**
     * 打印新闻
     * @param news
     */
    void printNews(String news);

}
