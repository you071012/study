package com.ukar.study.pegasus;

import com.ukar.study.pegasus.bean.AcctTransferResult;

public interface PegasusBaseServer<T,R> {
    R doInvoke(T t);
}
