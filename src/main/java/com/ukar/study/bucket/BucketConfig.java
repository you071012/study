package com.ukar.study.bucket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {

    @Bean(name = "byteBucket",initMethod = "init", destroyMethod = "destory")
    Bucket byteBucket(){
        return new ByteBucket(2048, 512);
    }
}
