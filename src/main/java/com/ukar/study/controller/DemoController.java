package com.ukar.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ukar.study.entity.SysParam;
import com.ukar.study.mapper.SysParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

        QueryWrapper<SysParam> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysParam::getParamKey, "RSA_PUB_KEY_test");
        SysParam sysParam = sysParamMapper.selectOne(queryWrapper);
        return sysParam == null ? "NULL" : sysParam.getParamValue();
    }

    @RequestMapping("/page")
    public List<SysParam> page(){
        QueryWrapper<SysParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(SysParam::getId);
        Page<SysParam> page = new Page<>(1,2);
        page = sysParamMapper.selectPage(page, queryWrapper);
        List<SysParam> records = page.getRecords();
        return records;
    }
}
