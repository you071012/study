package com.ukar.study.design.singleton;

public class SingletonInstance {

    private volatile static SingletonInstance instance;
    private SingletonInstance (){}

    /**
     * 双检锁/双重校验锁 模式
     * 注意此模式下 instance成员变量要加 volatile关键字
     *
     * 普通懒汉式、饿汉式、方法上加synchronized的线程安全懒汉式均不需要instance成员变量要加 volatile关键字
     *
     * 这种模式可能存在线程安全问题，java对象实例化会有重排序问题，静态成员变量不会（jvm会保证线程安全） 分析如下：
     * 1：对象未初始化完毕问题
     * 当线程A执行到 instance = new SingletonInstance(); 这一行，
     * 而线程B执行到外层"if (instance == null) "时，可能出现instance还未完成构造，但是此时不为null导致线程B获取到一个不完整的instance。
     *
     * 2：重排序问题
     * 对于 instance = new SingletonInstance(); 这一行代码，它分为三个步骤执行：
     * 1）分配一块内存空间
     * 2）在这块内存上初始化一个SingletonInstance的实例
     * 3）将声明的引用instance指向这块内存
     *
     * jvm可能会进行加载优化进行重新排序，步骤2），3）理论上没有任何依赖，但他俩都依赖步骤1），重排序后顺序如下
     * 1）分配一块内存空间
     * 2）将声明的引用instance指向这块内存
     * 3）在这块内存上初始化一个SingletonInstance的实例
     * 当线程A执行到步骤2），此时instance已经不是null了因为他指向了这块内存，这时线程B来执行方法，直接返回instance，可能就返回了null
     *
     *
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


    /**
     * 静态内部类单例模式，第一步私有化构造方法，上面已经私有化类，无需再写
     */
    private static class SingletonHolder{
        private static SingletonInstance instance = new SingletonInstance();
    }

    public static SingletonInstance getInstance2(){
        return SingletonHolder.instance;
    }

}
