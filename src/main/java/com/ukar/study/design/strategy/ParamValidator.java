package com.ukar.study.design.strategy;

/**
 * @author jia.you
 * @date 2020/04/17
 */
public class ParamValidator {
    private ValidationStrategy strategy;

    public ParamValidator(ValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean validator(String param){
        return this.strategy.execute(param);
    }
}
