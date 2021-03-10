package com.ukar.study.jdk.call;

import com.ukar.study.jdk.bo.UserBO;

public class QryUserCallBack2 implements CallBack<QryResult<UserBO>>{

    @Override
    public void doCall(QryResult<UserBO> qryResult) {
        System.out.println("QryUserCallBack2");
    }
}
