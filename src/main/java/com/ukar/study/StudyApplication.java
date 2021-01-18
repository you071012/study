package com.ukar.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.ukar.study.*"})
@EnableScheduling
@EnableAutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)//强制使用cglib代理,springboot 2.0以后默认使用的就是cglib代理
//@ServletComponentScan
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

}
