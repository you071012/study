package com.ukar.study.design.decorator;


public class ComponentTest {
    public static void main(String[] args){
        //普通人
        Component component = new ConcreteComponent();
        System.out.println(component.getManInfo());

        //对普通人进行增强，有车的人
        Component carManDecorator = new CarManDecorator(component);
        System.out.println(carManDecorator.getManInfo());

        //对普通人进行增强，有车的人
        Component houseManDecorator = new HouseManDecorator(component);
        System.out.println(houseManDecorator.getManInfo());
    }
}
