package com.ukar.study.controller;

import com.ukar.study.pipeline.TaskPipelineFactory;
import com.ukar.study.pipeline.demo.PayReqBO;
import com.ukar.study.pipeline.demo.PayRespBO;
import com.ukar.study.pipeline.demo.PayTaskPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pipeline")
public class PipelineController {

    @Autowired
    private TaskPipelineFactory taskPipelineFactory;

    @RequestMapping("/index")
    public String index(@RequestParam("sysId") String sysId){
        PayTaskPipeline taskPipeline = taskPipelineFactory.createTaskPipeline(PayTaskPipeline.class);
        PayReqBO payReqBO = new PayReqBO();
        payReqBO.setSysId(sysId);

        PayRespBO execute = taskPipeline.execute(payReqBO);
        return "OK";
    }
}
