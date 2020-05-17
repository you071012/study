package com.ukar.study.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jia.you
 * @date 2020/05/09
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/index")
    public String index(){
        return "OK";
    }
}
