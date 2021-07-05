package com.ukar.study.spring.simple;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/06/25/17:13
 * @Description:
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
    @Override
    public void onApplicationEvent(DemoEvent event) {
        System.out.println("注册成功，消息内容：" + event.getMsg());
    }
}
