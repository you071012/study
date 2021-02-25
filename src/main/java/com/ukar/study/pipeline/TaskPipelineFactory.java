package com.ukar.study.pipeline;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.Map;

@Component
@Slf4j
public class TaskPipelineFactory {
    private static Map<Class<? extends TaskPipeline>, TaskPipeline> taskPipelineCache = Maps.newConcurrentMap();

    /**因为InitializingBean的加载早于ApplicationContextAware，所以这里不能用SpringUtil来获取bean*/
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 统一创建任务流程类
     * @param taskPipelineClass
     * @param <T>
     * @return
     */
    public <T extends TaskPipeline> T createTaskPipeline(Class<T> taskPipelineClass) {

        try{
            //先缓存命中，命中后直接返回
            TaskPipeline taskPipeline = taskPipelineCache.get(taskPipelineClass);
            if(taskPipeline != null){
                return (T) taskPipeline;
            }
            taskPipeline = applicationContext.getBean(taskPipelineClass);

            if(taskPipeline == null){
                throw new IllegalArgumentException("[获取业务流程模板失败]，当前spring容器中没有该bean：" + taskPipelineClass.getSimpleName());
            }
            PipelineConfig pipelineConfig = taskPipelineClass.getAnnotation(PipelineConfig.class);
            if (pipelineConfig == null) {
                throw new IllegalArgumentException("[获取业务流程模板失败]，当前bean不包含任务流程：" + taskPipelineClass.getSimpleName());
            }
            Class<? extends CommonTask>[] commonTaskClazzs = pipelineConfig.commonTasks();

            if (commonTaskClazzs.length == 0) {
                throw new IllegalArgumentException( "[获取业务流程模板失败]，通过PipelineConfig注解需提供至少一个任务：" + taskPipelineClass.getSimpleName());
            }

            // 获得CommonTask，并注入到TaskPipeline
            CommonTask[] commonTasks = new CommonTask[commonTaskClazzs.length];
            for (int i = 0; i < commonTaskClazzs.length; i++) {
                // 不建议使用接口、抽象类 以防止Spring BeanFactory 歧义，如有需要可删除此限制
                if (commonTaskClazzs[i].isInterface() || Modifier.isAbstract(commonTaskClazzs[i].getModifiers())) {
                    throw new IllegalArgumentException("[获取业务流程模板失败]，PipelineConfig注解的CommonTask不能为接口或抽象类：" + taskPipelineClass.getSimpleName());
                }
                commonTasks[i] = applicationContext.getBean(commonTaskClazzs[i]);
            }
            taskPipeline.setCommonTasks(commonTasks);

            // 放入本地缓存
            taskPipelineCache.put(taskPipelineClass, taskPipeline);
            return (T) taskPipeline;
        }catch (Exception e){
            log.error("[获取业务流程模板失败]，系统未知异常：" + taskPipelineClass.getSimpleName());
            throw e;
        }
    }
}
