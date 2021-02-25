package com.ukar.study.pipeline.demo.task;

import com.ukar.study.pipeline.BusinessParamContextContainer;
import com.ukar.study.pipeline.CommonTask;
import com.ukar.study.pipeline.ParamType;
import com.ukar.study.pipeline.demo.PayReqBO;
import org.springframework.stereotype.Component;

@Component
public class PayFristTask implements CommonTask {
    @Override
    public void doTask() {
        System.out.println(BusinessParamContextContainer.getParam(ParamType.baseRequestData));
        System.out.println("this is PayFristTask......");
    }
}
