package com.ukar.study.jdk.call;

public class CallBackTest {
    public static void main(String[] args) {
        //普通回调
        PrintUserService printUserService = new PrintUserService();
        QryUserCallBack qryUserCallBack = new QryUserCallBack(printUserService);
        qryUserCallBack.printUser();
        qryUserCallBack.printUser2();

        QryUserCallBack2 qryUserCallBack2 = new QryUserCallBack2();
        printUserService.printUserBO(qryUserCallBack2);

        printUserService.printUserBO(s -> System.out.println(s));

    }
}
