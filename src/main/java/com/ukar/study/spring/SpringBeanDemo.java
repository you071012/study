package com.ukar.study.spring;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

@Component("SpringBeanDemo")
public class SpringBeanDemo implements BeanNameAware, BeanClassLoaderAware {
    @Override
    public void setBeanName(String s) {
        System.out.println("BeanNameAware加载");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("BeanClassLoaderAware加载");
    }
}
