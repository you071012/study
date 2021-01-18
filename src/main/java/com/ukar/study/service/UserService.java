package com.ukar.study.service;

import com.ukar.study.entity.User;
import com.ukar.study.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

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
}
