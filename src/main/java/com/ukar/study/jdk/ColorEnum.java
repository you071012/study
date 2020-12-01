package com.ukar.study.jdk;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 枚举会有隐藏属性name和ordinal
 * name：该枚举值的名称，如：RED，BLUE
 * ordinal：该枚举的序号，从0开始，但是慎用，如果使用反射添加一个枚举项可以任意设置ordinal，就会出现序号重复
 * 或者新加一个枚举到任意位置会打乱之前的顺序
 */
public enum ColorEnum {
    RED("red","红色"),
    BLUE("blue","黄色"),
    YELLOW("yellow","黄色"),
    ;

    private String color;

    private String desc;

    public static Map<String, ColorEnum> FAST_MAP;

    ColorEnum(String color, String desc) {
        this.color = color;
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public String getDesc() {
        return desc;
    }

    static {
        ColorEnum[] colorEnums = ColorEnum.values();
        FAST_MAP = new HashMap<>(16);
        for (ColorEnum enu : colorEnums) {
            FAST_MAP.put(enu.getColor(), enu);
        }
    }

    public static void main(String[] args) throws Exception {
        //打印枚举所有属性
        ColorEnum[] values = ColorEnum.values();
        Arrays.stream(values).forEach(t ->{
            String str = String.format("枚举name：%s，枚举序号：%d，color：%s，desc：%s", t.name(), t.ordinal(), t.getColor(), t.getDesc());
            System.out.println(str);
        });

        /*
         *  使用反射新增一个枚举项
         */

        //反射获取构造方法，其中前2个为隐藏参数，name和ordinal
        Constructor cstr = ColorEnum.class.getDeclaredConstructor(String.class, int.class, String.class,String.class);
        ReflectionFactory reflection = ReflectionFactory.getReflectionFactory();
        ColorEnum colorEnum =
                (ColorEnum) reflection.newConstructorAccessor(cstr).newInstance(new Object[]{"WHITE", 3,"white", "白色"});
        System.out.printf("%s = %d\n", colorEnum.toString(), colorEnum.ordinal());

        ColorEnum.FAST_MAP.put(colorEnum.getColor(), colorEnum);
        System.out.println(FAST_MAP.get("white"));
    }
}
