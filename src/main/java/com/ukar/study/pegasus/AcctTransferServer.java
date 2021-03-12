package com.ukar.study.pegasus;

import com.ukar.study.pegasus.bean.AcctTransferDTO;
import com.ukar.study.pegasus.bean.AcctTransferResult;

public class AcctTransferServer implements PegasusBaseServer<AcctTransferDTO, PgInvokeReturn<AcctTransferResult>>{

    @Override
    public PgInvokeReturn<AcctTransferResult> doInvoke(AcctTransferDTO acctTransferDTO) {
        //模拟调用具体服务
        AcctTransferResult result = new AcctTransferResult();

        //封装返回参数
        return new PgInvokeReturn<AcctTransferResult>(null, result);
    }
}
