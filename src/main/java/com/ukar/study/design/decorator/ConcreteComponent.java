package com.ukar.study.design.decorator;

/**
 * 装饰者基类的普通实现
 */
public class ConcreteComponent implements Component{
    @Override
    public String getManInfo() {
        return "这是一个普通的男人";
    }
}
