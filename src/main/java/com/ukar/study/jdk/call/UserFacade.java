package com.ukar.study.jdk.call;

import com.ukar.study.jdk.bo.UserBO;

public class UserFacade {

    QryResult<UserBO> queryUser(){
        UserBO userBO = new UserBO();
        userBO.setAge(18);
        userBO.setExt("第一个测试用户");
        userBO.setName("ukar");
        QryResult<UserBO> qryResult = new QryResult<UserBO>(userBO, "000");
        return qryResult;
    }
}
