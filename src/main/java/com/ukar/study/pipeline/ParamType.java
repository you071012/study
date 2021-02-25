package com.ukar.study.pipeline;


import com.ukar.study.pipeline.base.BaseReqBO;

public class ParamType<T> {

    /**结束步骤控制*/
    public static final ParamType<Boolean> endControl = new ParamType("endControl");

    /**跳过步骤控制*/
    public static final ParamType<Integer> skipControl = new ParamType("skipControl");

    /**通用请求*/
    public static final ParamType<BaseReqBO> baseRequestData = new ParamType<>("baseReq");

    private final String paramName;

    public ParamType(String name) {
        this.paramName = name;
    }

    public String getParamName() {
        return paramName;
    }

    @Override
    public String toString() {
        return paramName;
    }

}
