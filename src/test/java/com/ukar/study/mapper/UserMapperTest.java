package com.ukar.study.mapper;

import com.ukar.study.StudyApplication;
import com.ukar.study.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StudyApplication.class)
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void insert(){
        User user = new User();
        user.setName("ukar1");
        user.setRemark("插入第二条测试数据");
        userMapper.insert(user);

    }

}
