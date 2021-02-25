package com.ukar.study.pipeline.demo;

import com.ukar.study.pipeline.BusinessParamContextContainer;
import com.ukar.study.pipeline.CommonTask;
import com.ukar.study.pipeline.PipelineConfig;
import com.ukar.study.pipeline.TaskPipeline;
import com.ukar.study.pipeline.demo.task.PayFristTask;
import com.ukar.study.pipeline.demo.task.PayThreeTask;
import com.ukar.study.pipeline.demo.task.PayTwoTask;

import java.util.List;

@PipelineConfig(commonTasks = {
        PayFristTask.class,
        PayTwoTask.class,
        PayThreeTask.class
})
public class PayTaskPipeline extends TaskPipeline<PayReqBO, PayRespBO> {
    @Override
    protected int hookBeforeTask(int currentIndex, List<CommonTask> immutableTaskList) {
        if(currentIndex == 1){
            BusinessParamContextContainer.setParam("paytwotask before");
        }
        return currentIndex;
    }

    @Override
    protected int hookAfterTask(int nextIndex, List<CommonTask> immutableTaskList) {
        if(nextIndex == 1){
            BusinessParamContextContainer.setParam("payfristtask after");
        }
        return super.hookAfterTask(nextIndex, immutableTaskList);
    }
}
