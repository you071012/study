package com.ukar.study.jdk.call;

import com.ukar.study.jdk.bo.UserBO;

public class QryUserCallBack implements CallBack<QryResult<UserBO>>{

    private PrintUserService printUserService;

    public QryUserCallBack(PrintUserService printUserService) {
        this.printUserService = printUserService;
    }

    /**
     * 普通回调
     */
    public void printUser(){
        printUserService.printUserBO(this);
    }

    /**
     * 匿名回调
     */
    public void printUser2(){
        printUserService.printUserBO(new CallBack<QryResult<UserBO>>() {
            @Override
            public void doCall(QryResult<UserBO> t) {
                System.out.println(t.getResult().getExt());
            }
        });
    }

    /**
     * 回调函数打印userBo信息
     * @param t
     */
    @Override
    public void doCall(QryResult<UserBO> t) {
        System.out.println(t.getResult());
    }
}
