package com.ukar.study.pipeline;

public interface CommonTask {
    /**
     * 进行一般业务操作，若想被 {@link com.ukar.study.pipeline.PipelineConfig} 装配给特定任务流程
     * 需注入到 Spring容器中 {@code @Component}、{@code @Service}
     *
     * <p>在任务流程中的获取、设置、传递参数 可使用如下方法
     * {@link com.ukar.study.pipeline.BusinessParamContextContainer#getParam(ParamType)} 等重载方法
     * {@link com.ukar.study.pipeline.BusinessParamContextContainer#setParam(ParamType, Object)} 等重载方法
     * 放置在线程上下文中的对象将在 任务流程结束后清除
     *
     *
     * <p>约定1：任务流程开始前默认将 流程执行请求BO放置到 {@link ParamType#baseRequestData} 及 P.class
     * 可通过 {@code getParam(ParamType.baseRequestData)} 或 {@code getParam(P.class)} 获取
     *
     * <p>约定2：任务流程结束的最后一个 CommonTask 或 最终的{@code hookAfterTask()} 需将任务流程的响应BO放置到容器中
     * 可通过 {@code setParam(P.class, p)} 或 {@code setParam(p)} （第二种方式请确保响应BO实例不是CGLIB、JDK代理生成）
     *
     *
     * <p>新增、修改功能 可复用已有的CommonTask，但虽然逻辑一样但参数、类型可能有出入
     * 可在{@link com.ukar.study.pipeline.TaskPipeline}的{@code hookBeforeTask()}、{@code hookAfterTask()}做些特异的操作
     *
     * @implNote Implementations can put params or results into BusinessParamContextContainer
     */
    void doTask();
}
