package com.ukar.study.pegasus;

import com.ukar.study.pegasus.bean.AcctTransferDTO;
import com.ukar.study.pegasus.bean.AcctTransferResult;
import com.ukar.study.pegasus.bean.PgInvokeReturn;

public class AcctTransferServer {

    private PgInvokeReturnProcess<AcctTransferResult, PgInvokeReturn<AcctTransferResult>> process;

    public AcctTransferServer(PgInvokeReturnProcess<AcctTransferResult, PgInvokeReturn<AcctTransferResult>> event) {
        //使用默认事件
        if(event == null){
            event = acctTransferResult -> new PgInvokeReturn<AcctTransferResult>();
        }
        this.process = process;
    }

    public PgInvokeReturn<AcctTransferResult> doInvoke(AcctTransferDTO acctTransferDTO) {
        //模拟调用具体服务
        AcctTransferResult result = new AcctTransferResult();
        result.setReturnCode("000");
        //封装返回参数
        return this.process.doReturn(result);
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
