package com.ukar.study.redis;

import lombok.Data;

@Data
public class Lock {
    private String lockKey;

    private String lockVal;


}
