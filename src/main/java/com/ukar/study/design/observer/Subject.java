package com.ukar.study.design.observer;

/**
 * @author jia.you
 * @date 2020/04/17
 *
 * 观察者模式负责监听的接口
 */
public interface Subject {

    /**
     * 注册观察者，注意此方法目前的实现为线程不安全
     * @param o
     */
    void registerObserver(Observer o);

    /**
     * 推送新闻，唤醒已注册的所有观察者
     * @param news
     */
    void notifyObservers(String news);
}
