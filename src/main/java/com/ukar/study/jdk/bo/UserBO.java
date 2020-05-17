package com.ukar.study.jdk.bo;

import lombok.Data;

/**
 * @author jia.you
 * @date 2020/03/25
 */

@Data
public class UserBO {

    private String name;

    private int age;

    private String ext;

    public UserBO() {
    }

    public UserBO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserBO(String name, int age, String ext) {
        this.name = name;
        this.age = age;
        this.ext = ext;
    }
}
