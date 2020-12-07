package com.ukar.study.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ApplicationListener：spring事件监听器
 *
 * ContextRefreshedEvent：容器上下文配置加载完成后处理事件，容器还未启动完成
 *
 * 为了防止父子容器重复触发的风险，可以简单处理
 */
@Component
public class ContextRefreshedEventDemo implements ApplicationListener<ContextRefreshedEvent> {
    private volatile AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(!atomicBoolean.compareAndSet(false,true)){
            return;
        }

        // FIXME 这里只做打印演示
        System.out.println("容器上下文配置加载完成后处理事件");
    }
}
