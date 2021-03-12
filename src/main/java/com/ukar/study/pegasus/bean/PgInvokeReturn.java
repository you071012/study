package com.ukar.study.pegasus.bean;

import lombok.Data;

@Data
public class PgInvokeReturn<T> {
    Throwable throwable;
    T returnDTO;

    public PgInvokeReturn(Throwable throwable, T returnDTO) {
        this.throwable = throwable;
        this.returnDTO = returnDTO;
    }

    public PgInvokeReturn() {
    }

    public static <T> PgInvokeReturn<T> succ(Throwable throwable, T t){
        return new PgInvokeReturn(null, t);
    }

    public static <T> PgInvokeReturn<T> fail(Throwable throwable){
        return new PgInvokeReturn(throwable, null);
    }
}
