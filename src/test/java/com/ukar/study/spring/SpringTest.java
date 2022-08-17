package com.ukar.study.spring;

import com.ukar.study.StudyApplication;
import com.ukar.study.spring.simple.DemoPusher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/07/14/09:32
 * @Description:
 */
@SpringBootTest(classes = StudyApplication.class)
@RunWith(SpringRunner.class)
public class SpringTest {

    @Autowired
    private DemoPusher demoPusher;

    @Test
    public void test() throws InterruptedException {
        demoPusher.pushlish("你好啊1");
        demoPusher.pushlish("你好啊2");
        demoPusher.pushlish("你好啊3");

        Thread.currentThread().join();
    }
}
