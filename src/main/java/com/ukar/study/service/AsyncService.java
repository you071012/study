package com.ukar.study.service;

import com.ukar.study.jdk.LambdDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jia.you
 * @date 2020/06/19
 */
@Component
@Slf4j
public class AsyncService {

    @Async("defaultExecutor")
    public void test1() {
        log.info("test1..................run................");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test1.................." + LambdDemo.list.size());
        log.info("test1..................end................");
    }

    @Async("defaultExecutor")
    public void test2( ){
        log.info("test2..................run................");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test2.................." + LambdDemo.list.size());
        log.info("test2..................end................");
    }

    @Async("defaultExecutor")
    public void test3(FunctionService functionService, String str){
        log.info("test3..................run................");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int len = functionService.getLen(str);
        System.out.println("test3.................." + LambdDemo.list.size());
        System.out.println("test3执行计算长度为：" + len);
    }
}
