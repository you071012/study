package com.ukar.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ukar.study.entity.User;
import com.ukar.study.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private UserMapper userMapper;

    @Value("${server.port}")
    private String aa;

    @RequestMapping("/index")
    public String index(){

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(User::getName, "ukar");
        User user = userMapper.selectOne(queryWrapper);
        return user == null ? "NULL" : user.toString();
    }

    @RequestMapping("/page")
    public List<User> page(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(User::getId);
        Page<User> page = new Page<>(1,1);
        page = userMapper.selectPage(page, queryWrapper);
        List<User> records = page.getRecords();
        return records;
    }
}
