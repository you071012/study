package com.ukar.study.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ukar.study.datasource.annotation.DataSourceAnnotation;
import com.ukar.study.datasource.enums.DataSourceEnum;
import com.ukar.study.entity.User;
import com.ukar.study.mapper.UserMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DynamicUserService implements InitializingBean {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User :: getId, 1);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    public User selectMaster(String name){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User :: getName, "ukar");
        return userMapper.selectOne(queryWrapper);
    }

    @DataSourceAnnotation(DataSourceEnum.Slave01)
    public User selectSlave01(String name){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User :: getName, "ukar");
        return userMapper.selectOne(queryWrapper);
    }

    @DataSourceAnnotation(DataSourceEnum.Slave02)
    public User selectSlave02(String name){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User :: getName, "ukar");
        return userMapper.selectOne(queryWrapper);
    }

    @DataSourceAnnotation(DataSourceEnum.Slave)
    public User selectRandom(String name){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User :: getName, "ukar");
        return userMapper.selectOne(queryWrapper);
    }
}
