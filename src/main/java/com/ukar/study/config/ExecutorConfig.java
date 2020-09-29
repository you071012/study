package com.ukar.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author jia.you
 * @date 2020/06/18
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    /**
     * 可以配置多个Executor，指定不通的bean名称，使用@Async时可以指定使用哪个Executor
     * Async修饰的方法不能在同一个类中调用，不能私有
     * @return
     */
    @Bean(name = "defaultExecutor")
    public Executor executor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(9);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(100);
        //线程最大空闲时间
        executor.setKeepAliveSeconds(10);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setThreadNamePrefix("study-thread-");
        //初始化加载，如果有需要初始化加载的任务
//        executor.initialize();
        return executor;
    }
}
