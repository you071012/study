package com.ukar.study.design.observer;

import com.ukar.study.design.observer.impl.BbcObserver;
import com.ukar.study.design.observer.impl.CctvObserver;
import com.ukar.study.design.observer.impl.SubjectImpl;

/**
 * @author jia.you
 * @date 2020/04/17
 */
public class ObserverTest {

    public static void main(String[] args) {
        Subject subject = new SubjectImpl();
        subject.registerObserver(new CctvObserver());
        subject.registerObserver(new BbcObserver());

        subject.notifyObservers("今日头条播报中...");

    }

}
