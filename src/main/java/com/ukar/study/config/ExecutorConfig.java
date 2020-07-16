package com.ukar.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

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
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(10);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setThreadNamePrefix("study-thread-");
        return executor;
    }
}
