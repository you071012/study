package com.ukar.study.config;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

@Getter
public class Demo {

    transient String name;
    String remark;
    int age;

    public Demo(String name, String remark, int age) {
        this.name = name;
        this.remark = remark;
        this.age = age;
    }

    public static void main(String[] args) {
        System.out.println(tableSizeFor(12));
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;
}
