package com.ukar.study.controller;

import com.ukar.study.service.AsyncService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        System.out.println("test..................run................");
        asyncService.test1();
        asyncService.test2();
        System.out.println("test..................end................");
        return "OK";
    }
}
