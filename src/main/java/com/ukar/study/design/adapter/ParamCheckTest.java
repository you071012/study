package com.ukar.study.design.adapter;

import org.apache.commons.lang3.StringUtils;

public class ParamCheckTest {

    public static void main(String[] args) {
        String name = "ukar";
        new ParamCheckAdapter(() -> new VerifyResult(StringUtils.isNotBlank(name), "姓名不能为空")).check();

        new ParamCheckAdapter((() -> new VerifyResult(name.length() <= 2, "姓名最大2位"))).check();
    }
}
