package com.ukar.study.design.strategy.impl;

import com.ukar.study.design.strategy.ValidationStrategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jia.you
 * @date 2020/04/17
 */
public class NumberMatchCase implements ValidationStrategy {
    @Override
    public boolean execute(String param) {
        String regex = "^\\d+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(param);
        return matcher.matches();
    }
}
