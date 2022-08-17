package com.ukar.study.spring.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/06/25/17:13
 * @Description:
 */
@Component
@Slf4j
public class DemoListener implements ApplicationListener<DemoEvent> {

    /*
     * 使用@Async可以使消息异步执行
     */
    @Override
    @Async(value = "defaultExecutor")
    public void onApplicationEvent(DemoEvent event) {
        log.info("注册成功，消息内容：{}", event.getMsg());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("注册结束，消息内容：{}", event.getMsg());
    }
}
