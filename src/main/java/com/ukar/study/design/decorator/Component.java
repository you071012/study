package com.ukar.study.design.decorator;

/**
 * 定义一个装饰者基类接口
 */
public interface Component {

    /**
     * 获取一个男人的信息
     * @return
     */
    String getManInfo();

    /**
     * 装饰者模式组成部分
     * 1：接口基类 Component
     * 2：接口的普通实现类（待增强类） ConcreteComponent --> 实现Component
     * 3：装饰者类：Decorator --> 实现Component（需求接收一个接口基类参数，用于执行普通类方法）
     * 4：增强类：CarManDecorator，HouseManDecorator --> 继承Decorator
     *
     * 实际使用中可以选择使用哪种类进行增强
     */
}
