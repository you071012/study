package com.ukar.study.mapper;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ukar.study.StudyApplication;
import com.ukar.study.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = StudyApplication.class)
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void insert(){
        User user = new User();
        user.setName("ukar1");
        user.setRemark("aaaa");
        userMapper.insert(user);
    }

    @Test
    public void batchInsert(){
        User user = new User();
        user.setId(IdWorker.getId());
        user.setName("ukar1");
        user.setRemark("aaaa");

        List<User> list = new ArrayList<>();
        list.add(user);
        userMapper.batchInsert(list);
    }

}
