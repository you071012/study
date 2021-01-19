package com.ukar.study.design.observer.impl;

import com.ukar.study.design.observer.Observer;
import com.ukar.study.design.observer.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jia.you
 * @date 2020/04/17
 */
public class SubjectImpl implements Subject {

    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String news) {
        observers.forEach(t -> t.printNews(news));
    }
}
