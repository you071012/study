package com.ukar.study.design.decorator;

/**
 * 定义一个装饰者类，实现类基类接口，传入一个基类
 */
public class Decorator implements Component{
    private Component component = null;

    public Decorator(Component component){
        this.component = component;
    }

    @Override
    public String getManInfo() {
        return this.component.getManInfo();
    }
}
