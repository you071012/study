package com.ukar.study.design.singleton;

public class SingletonInstance {

    private volatile static SingletonInstance instance;
    private SingletonInstance (){}

    /**
     * 双检锁/双重校验锁 模式
     * 注意此模式下 instance成员变量要加 volatile关键字
     *
     * 普通懒汉式、饿汉式、方法上加synchronized的线程安全懒汉式均不需要instance成员变量要加 volatile关键字
     * @return
     */
    public static SingletonInstance getInstance(){
        if(instance == null){
            synchronized (SingletonInstance.class){
                if(instance == null){
                    instance = new SingletonInstance();
                }
            }
        }
        return instance;
    }
}
