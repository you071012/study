package com.ukar.study.design.decorator;

/**
 * 定义一个有车的增强类
 */
public class CarManDecorator extends Decorator{
    public CarManDecorator(Component component) {
        super(component);
    }

    public String strengthen(String manInfo){
        return manInfo + "并且有车";
    }

    @Override
    public String getManInfo() {
        String manInfo = super.getManInfo();
        return strengthen(manInfo);
    }
}
