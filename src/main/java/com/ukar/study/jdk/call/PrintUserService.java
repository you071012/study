package com.ukar.study.jdk.call;

import com.ukar.study.jdk.bo.UserBO;

public class PrintUserService {

    public void printUserBO(CallBack callBack){
        UserFacade userFacade = new UserFacade();
        QryResult<UserBO> qryResult = userFacade.queryUser();
        callBack.doCall(qryResult);
    }

}
