package com.ukar.study.design.strategy;

import com.ukar.study.design.strategy.impl.LetterMatchCase;
import com.ukar.study.design.strategy.impl.NumberMatchCase;

/**
 * @author jia.you
 * @date 2020/04/17
 */
public class StrategyTest {

    public static void main(String[] args) {
        StrategyTest strategyTest = new StrategyTest();
        strategyTest.normalStrategyTest();
        strategyTest.lambdStrategyTest();
    }

    /**
     * 普通调用方式策略模式测试
     */
    public void normalStrategyTest(){
        ParamValidator letterValidator = new ParamValidator(new LetterMatchCase());
        ParamValidator numValidator = new ParamValidator(new NumberMatchCase());
        boolean letterCase = letterValidator.validator("abc");
        boolean numCase = numValidator.validator("123");
        System.out.println("normal调用：" + letterCase);
        System.out.println("normal调用：" + numCase);
    }

    /**
     * lambd调用方式策略模式测试，不需要具体实现类，lambd直接指定实现
     * 所有针对只有一个接口的类，使用lambd会使策略模式更简洁，只需要定义策略接口
     */
    public void lambdStrategyTest(){
        ParamValidator letterValidator = new ParamValidator(param -> param.matches("^[a-zA-Z]+$"));
        ParamValidator numValidator = new ParamValidator(param -> param.matches("^\\d+$"));
        boolean letterCase = letterValidator.validator("abc");
        boolean numCase = numValidator.validator("123");
        System.out.println("lambd调用：" + letterCase);
        System.out.println("lambd调用：" + numCase);
    }
}
