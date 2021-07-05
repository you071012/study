package com.ukar.study.spring.simple;

import org.springframework.context.ApplicationEvent;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/06/25/17:13
 * @Description:
 */
public class DemoEvent extends ApplicationEvent {

    private String msg;

    public DemoEvent(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
