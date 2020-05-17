package com.ukar.study.design.strategy;

/**
 * @author jia.you
 * @date 2020/04/17
 *
 * 策略模式代表了解决一类算法的通用解决方案，你可以在运行时选择使用哪种方案。
 * eg：数字匹配算法基类 ValidationStrategy 包含多种实现
 *     实现一：匹配输入字符是否是英文字母 --> LetterMatchCase
 *     实现二：匹配输入字符是否是数字 --> NumberMatchCase
 */
public interface ValidationStrategy {

    /**
     * 执行方法
     * @param param
     * @return
     */
    boolean execute(String param);
}
