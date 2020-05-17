package com.ukar.study.jdk;

import com.ukar.study.jdk.bo.UserBO;

/**
 * @author jia.you
 * @date 2020/04/09
 */
@FunctionalInterface
public interface UserProcess {
    String process(UserBO userBO);
}
