package com.ukar.study.pegasus;

public interface PgInvokeReturnEvent<T, R> {

    R doReturn(T t);
}
