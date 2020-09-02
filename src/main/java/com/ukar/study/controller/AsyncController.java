package com.ukar.study.controller;

import com.ukar.study.jdk.LambdDemo;
import com.ukar.study.service.AsyncService;
import com.ukar.study.service.FunctionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jia.you
 * @date 2020/06/19
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Resource
    private AsyncService asyncService;

    @RequestMapping("/index")
    public String test(){
        System.out.println("test..................end................");
        List<String> list = LambdDemo.list;
        list.add("111");
        System.out.println("test..................run................");
        asyncService.test1();
        asyncService.test2();

        FunctionService functionService = t->t.length() * 2;
        asyncService.test3(functionService, "ukar");


        return "OK";
    }

}
