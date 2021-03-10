package com.ukar.study.jdk.call;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QryResult<T> {
    private T result;
    private String respCode;

}
