package com.ukar.study.controller;

import com.ukar.study.entity.SysParamDO;
import com.ukar.study.mapper.SysParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jia.you
 * @date 2020/05/09
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private SysParamMapper sysParamMapper;

    @RequestMapping("/index")
    public String index(){
        SysParamDO sysParamDO = sysParamMapper.selectByParamKey("RSA_PUB_KEY_test");
        return sysParamDO == null ? "null" : sysParamDO.getParamValue();
    }
}
