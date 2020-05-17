package com.ukar.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ukar.*"})
class StudyApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(StudyApplicationTest.class, args);
    }
}
