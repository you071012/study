package com.ukar.study.pegasus;

public interface PgInvokeReturnProcess<T, R> {

    R doReturn(T t);
}
