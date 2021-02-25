package com.ukar.study.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ukar.study.entity.User;
import com.ukar.study.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Transactional
    public Integer update(int time, String remark){
        int i = userMapper.updateByWhere(remark);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

    public void transactionTemplateTest(User user){
        transactionTemplate.execute((status) -> {
            try{
                userMapper.insert(user);

                LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(User :: getName, user.getName());
                userMapper.selectOne(queryWrapper);


                UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
                userUpdateWrapper.eq("name", user.getName());
                userUpdateWrapper.set("name", "ukar911");
                userMapper.update(null, userUpdateWrapper);
                return 1;
            }catch (Exception e){
                status.setRollbackOnly();
                throw e;
            }
        });
    }
}
