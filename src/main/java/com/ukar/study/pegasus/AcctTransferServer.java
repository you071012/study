package com.ukar.study.pegasus;

import com.ukar.study.pegasus.bean.AcctTransferDTO;
import com.ukar.study.pegasus.bean.AcctTransferResult;
import com.ukar.study.pegasus.bean.PgInvokeReturn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class AcctTransferServer {

    private PgInvokeReturnEvent<AcctTransferResult, PgInvokeReturn<AcctTransferResult>> event;

    public AcctTransferServer(PgInvokeReturnEvent<AcctTransferResult, PgInvokeReturn<AcctTransferResult>> event) {
        //使用默认事件
        if(event == null){
            event = acctTransferResult -> new PgInvokeReturn<AcctTransferResult>();
        }
        this.event = event;
    }

    public PgInvokeReturn<AcctTransferResult> doInvoke(AcctTransferDTO acctTransferDTO) {
        //模拟调用具体服务
        AcctTransferResult result = new AcctTransferResult();
        result.setReturnCode("000");
        //封装返回参数
        return this.event.doReturn(result);
    }

    public static void main(String[] args) {
        AcctTransferServer acctTransferServer = new AcctTransferServer(acctTransferResult -> {
            PgInvokeReturn<AcctTransferResult> pgInvokeReturn = new PgInvokeReturn<>();
            pgInvokeReturn.setReturnDTO(acctTransferResult);
            return pgInvokeReturn;
        });

        PgInvokeReturn<AcctTransferResult> result = acctTransferServer.doInvoke(null);
        System.out.println(result);

    }
}
