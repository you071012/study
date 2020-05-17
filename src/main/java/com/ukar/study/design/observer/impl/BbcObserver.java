package com.ukar.study.design.observer.impl;

import com.ukar.study.design.observer.Observer;

/**
 * @author jia.you
 * @date 2020/04/17
 */
public class BbcObserver implements Observer {
    @Override
    public void printNews(String news) {
        System.out.println("BBC接收到新消息：" + news);
    }
}
