package com.ukar.study.pipeline;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc:Created with IDEA
 * Author:tony.cai
 * Date:2019-06-10
 * Time:23:14
 **/
public class ParamContext {

    public Map<String, Object> paramsMap = new HashMap<>();

    public Map<String, Object> getParamsMap() {
        return paramsMap;
    }

    public <T> void setParam(ParamType<T> paramType, T value) {
        paramsMap.put(paramType.getParamName(), value);
    }

    public <T> void setParam(T value) {
        paramsMap.put(value.getClass().getName(), value);
    }

    public <C, T extends C> void setParam(Class<C> clazz, T value) {
        paramsMap.put(clazz.getName(), value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getParam(ParamType<T> paramType) {
        return (T) paramsMap.get(paramType.getParamName());
    }

    @SuppressWarnings("unchecked")
    public <T> T getAndRemoveParam(ParamType<T> paramType) {
        return (T) paramsMap.remove(paramType.getParamName());
    }

    @SuppressWarnings("unchecked")
    public <T> T getParam(Class<T> paramClass) {
        return (T) paramsMap.get(paramClass.getName());
    }

    public void clear() {
        paramsMap.clear();
    }
}
