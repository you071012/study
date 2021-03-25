package com.ukar.study.pegasus.bean;

import lombok.Data;

@Data
public class PgInvokeReturn<T> {
    private T returnDTO;
    private String returnCode;
    private String returnDesc;

    public PgInvokeReturn(InvokeReturnEnum invokeReturnEnum) {
        this.returnCode = invokeReturnEnum.getReturnCode();
        this.returnDesc = invokeReturnEnum.getReturnDesc();
    }

    public PgInvokeReturn(String returnCode, String returnDesc, T returnDTO) {
        this.returnDTO = returnDTO;
        this.returnCode = returnCode;
        this.returnDesc = returnDesc;
    }

    public PgInvokeReturn() {
    }

    public static <T> PgInvokeReturn<T> succ(T t){
        return new PgInvokeReturn(InvokeReturnEnum.SYS_SUCCESS.getReturnCode(), InvokeReturnEnum.SYS_SUCCESS.getReturnDesc(), t);
    }

    public static <T> PgInvokeReturn<T> fail(InvokeReturnEnum invokeReturnEnum){
        return new PgInvokeReturn(invokeReturnEnum);
    }
}
