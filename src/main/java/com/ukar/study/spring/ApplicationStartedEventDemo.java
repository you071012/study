package com.ukar.study.spring;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ApplicationListener：spring事件监听器
 *
 * ApplicationStartedEvent：容器启动完成后处理事件
 *
 * 为了防止父子容器重复触发的风险，可以简单处理
 */
@Component
public class ApplicationStartedEventDemo implements ApplicationListener<ApplicationStartedEvent> {

    private volatile AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        if(!atomicBoolean.compareAndSet(false,true)){
            return;
        }

        // FIXME 这里只做打印演示
        System.out.println("spring boot 启动监听类执行完毕");
    }
}
