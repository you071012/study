package com.ukar.study.pipeline;

import com.ukar.study.pipeline.base.BaseReqBO;
import com.ukar.study.pipeline.base.BaseRespBO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TaskPipeline<P extends BaseReqBO, R extends BaseRespBO> implements InitializingBean {

    @Setter
    @Getter
    protected CommonTask[] commonTasks;

    private List<CommonTask> immutableTaskList = null;

    @Autowired
    protected TaskPipelineFactory taskPipelineFactory;

    private Class requestBOClazz;
    private Class responseBOClazz;

    public R execute(P baseReqBO) {
        try{
            if(commonTasks == null || commonTasks.length == 0){
                throw new IllegalArgumentException("[运行任务处理流程失败]，此TaskPipeline未正确设置处理任务");
            }
            if(immutableTaskList == null || immutableTaskList.size() == 0){
                immutableTaskList = Collections.unmodifiableList(Arrays.asList(commonTasks));
            }
            BusinessParamContextContainer.setParam(ParamType.baseRequestData, baseReqBO);
            BusinessParamContextContainer.setParam(getRequestBOClazz(), baseReqBO);
            int index = 0;
            do {

                //任务前置操作
                index = hookBeforeTask(index, immutableTaskList);
                if (index >= commonTasks.length) {
                    break;
                }
                CommonTask currentTask = commonTasks[index++];
                //执行任务
                currentTask.doTask();

                //任务后置操作
                index = hookAfterTask(index, immutableTaskList);

            }while (index < commonTasks.length);

            return (R)BusinessParamContextContainer.getParam(getResponseBOClazz());
        }catch (Exception e){
            throw e;
        }finally {
            //清理hreadLocal内容，防止oom
            BusinessParamContextContainer.clearTransContext();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        taskPipelineFactory.createTaskPipeline(this.getClass());
        log.info("[初始业务流程模板] [{}]流程初始化成功，流程中任务数=[{}]，",
                this.getClass().getSimpleName(), commonTasks == null ? 0 : commonTasks.length);
    }

    /**
     * 每次任务处理前钩子函数，用于自定义操作
     *
     * @param currentIndex
     * @param immutableTaskList 此任务处理流程的任务，不可变动
     * @return 下一个处理位置，大于等于 immutableTaskList.size() 将终结流程
     */
    protected int hookBeforeTask(int currentIndex, List<CommonTask> immutableTaskList) {
        return currentIndex;
    }

    /**
     * 每次任务处理后钩子函数，用于自定义操作
     *
     * @param nextIndex         下一个索引，1-size
     * @param immutableTaskList 此任务处理流程的任务，不可变动
     * @return 下一个处理位置，大于等于 immutableTaskList.size() 将终结流程
     * @implNote next取值1-size，故immutableTaskList.get(nextIndex)可能数组越界
     */
    protected int hookAfterTask(int nextIndex, List<CommonTask> immutableTaskList) {
        // 终结此任务处理流程
        Boolean endControl = BusinessParamContextContainer.getAndRemoveParam(ParamType.endControl);
        if (endControl != null && endControl) {
            return immutableTaskList.size();
        }
        // 跳过接下的n个处理任务
        Integer skipControl = BusinessParamContextContainer.getAndRemoveParam(ParamType.skipControl);
        if (skipControl != null && (nextIndex + skipControl) <= immutableTaskList.size()) {
            return nextIndex + skipControl;
        }
        return nextIndex;
    }

    @SuppressWarnings("unchecked")
    public Class<P> getRequestBOClazz() {
        if (requestBOClazz == null) {
            Type superClass = this.getClass().getGenericSuperclass();
            Type requestBOType = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            requestBOClazz = (Class) requestBOType;
        }
        return requestBOClazz;
    }

    @SuppressWarnings("unchecked")
    public Class<R> getResponseBOClazz() {
        if (responseBOClazz == null) {
            Type superClass = this.getClass().getGenericSuperclass();
            Type responseBOType = ((ParameterizedType) superClass).getActualTypeArguments()[1];
            responseBOClazz = (Class) responseBOType;
        }
        return responseBOClazz;
    }
}
