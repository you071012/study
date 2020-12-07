package com.ukar.study.design.decorator;

/**
 * 定义一个有房子的增强类
 */
public class HouseManDecorator extends Decorator{
    public HouseManDecorator(Component component) {
        super(component);
    }

    public String strengthen(String manInfo){
        return manInfo + "并且有房子";
    }

    @Override
    public String getManInfo() {
        String manInfo = super.getManInfo();
        return strengthen(manInfo);
    }
}
