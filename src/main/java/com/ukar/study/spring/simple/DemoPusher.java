package com.ukar.study.spring.simple;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/06/25/17:14
 * @Description:
 */

@Component
public class DemoPusher implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void pushlish(String msg) {
        applicationContext.publishEvent(new DemoEvent(msg));
        applicationContext.publishEvent(new DemoEvent(msg));
        applicationContext.publishEvent(new DemoEvent(msg));
    }


}
