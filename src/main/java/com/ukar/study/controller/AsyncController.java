package com.ukar.study.controller;

import com.ukar.study.jdk.LambdDemo;
import com.ukar.study.service.AsyncService;
import com.ukar.study.service.FunctionService;
import com.ukar.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

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

    @RequestMapping("/index1")
    public String test1(){
        String a = userService.update(8000, "index1") + "";
        System.out.printf("index1执行完毕");
        return a;
    }

    @RequestMapping("/index2")
    public String test2(){
        String a = userService.update(100, "index2") + "";
        System.out.printf("index2执行完毕");
        return a;
    }

}
