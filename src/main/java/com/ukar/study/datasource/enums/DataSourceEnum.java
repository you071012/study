package com.ukar.study.datasource.enums;

/**
 * Created by jyou on 2018/9/11.
 *
 * @author jyou
 */
public enum DataSourceEnum {
    //主库
    Master("master"),

    //从库标识，用户轮询从库使用
    Slave("slave"),

    //从库01
    Slave01("slave01"),

    //从库02
    Slave02("slave02"),
    ;

    private DataSourceEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
