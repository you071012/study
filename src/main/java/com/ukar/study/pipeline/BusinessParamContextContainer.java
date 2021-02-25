package com.ukar.study.pipeline;


/**
 * Desc:Created with IDEA
 * Author:tony.cai
 * Date:2019-06-10
 * Time:23:09
 **/
public class BusinessParamContextContainer {

    /**
     * 线程本地变量
     */
    public static final ThreadLocal<ParamContext> transContext = ThreadLocal.withInitial(ParamContext::new);

    /**
     * 设置参数
     *
     * @param <T>
     * @param paramType
     * @param value
     */
    public static <T> void setParam(ParamType<T> paramType, T value) {
        transContext.get().setParam(paramType, value);
    }

    public static <T> void setParam(T value) {
        transContext.get().setParam(value);
    }

    public static <C, T extends C> void setParam(Class<C> clazz, T value) {
        transContext.get().setParam(clazz, value);
    }

    /**
     * 获取参数
     *
     * @param <T>
     * @param paramType
     * @return
     */
    public static <T> T getParam(ParamType<T> paramType) {
        return transContext.get().getParam(paramType);
    }

    public static <T> T getAndRemoveParam(ParamType<T> paramType) {
        return transContext.get().getAndRemoveParam(paramType);
    }

    public static <T> T getParam(Class<T> clazz) {
        return transContext.get().getParam(clazz);
    }

    /**
     * 释放线程变量
     */
    public static void clearTransContext() {
        transContext.get().clear();
    }

}
