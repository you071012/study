package com.ukar.study.pegasus.bean;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/03/16/15:30
 * @Description:
 */
public enum InvokeReturnEnum {

    /**
     * 通用返回码
     */
    SYS_SUCCESS("000","调用成功"),
    SYS_FAILURE("004","调用失败"),
    SYS_TIMEOUT("300", "调用系统超时"),
    SYS_EXCEPTION("099", "调用系统异常"),
    ;

    private String returnCode;
    private String returnDesc;

    InvokeReturnEnum(String returnCode, String returnDesc) {
        this.returnCode = returnCode;
        this.returnDesc = returnDesc;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnDesc() {
        return returnDesc;
    }
}
