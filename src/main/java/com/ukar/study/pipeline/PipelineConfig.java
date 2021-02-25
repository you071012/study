package com.ukar.study.pipeline;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PipelineConfig {

    /**
     * 不可为空，且给定的 {@link CommonTask} 需注入Spring 容器
     */
    Class<? extends CommonTask>[] commonTasks();
}