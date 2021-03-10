package com.ukar.study.jdk.call;

import com.ukar.study.jdk.bo.UserBO;

/**
 * 回调函数
 * 假设回调类A中有a(),callback()两个方法，类B中有b(CallBack callback)方法，
 * 回调函数就是a() -> b(),b() -> callback()
 * 适用于一个通用接口有多种处理模式，PrintUserService方法接收一个CallBack，通过传入不同的实现类或匿名对象，执行不同的回调
 * 详情见CallBackTest测试用例
 */
@FunctionalInterface
public interface CallBack<T>{

    void doCall(T t);
}
