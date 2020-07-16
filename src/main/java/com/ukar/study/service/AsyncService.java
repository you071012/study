package com.ukar.study.service;

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
        log.info("test2..................end................");
    }
}
