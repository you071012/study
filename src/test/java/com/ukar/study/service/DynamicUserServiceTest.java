package com.ukar.study.service;
import java.time.LocalDateTime;

import com.ukar.study.StudyApplication;
import com.ukar.study.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StudyApplication.class)
@RunWith(SpringRunner.class)
public class DynamicUserServiceTest {
    @Autowired
    private DynamicUserService dynamicUserService;

    @Test
    public void testSelectMaster(){
        User user = dynamicUserService.selectMaster("ukar");
        System.out.println(user);
    }

    @Test
    public void testSelectSlave01(){
        User user = dynamicUserService.selectSlave01("ukar");
        System.out.println(user);
    }

    @Test
    public void testSelectSlave02(){
        User user = dynamicUserService.selectSlave02("ukar");
        System.out.println(user);
    }

    @Test
    public void testSelectSlave(){
        for(int i = 0; i < 10; i++){
            User user = dynamicUserService.selectRandom("ukar");
            System.out.println(user);
        }
    }
}
