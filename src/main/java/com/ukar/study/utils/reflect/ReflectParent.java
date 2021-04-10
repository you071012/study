package com.ukar.study.utils.reflect;

import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/03/30/13:34
 * @Description:
 */
@Data
@ToString
public class ReflectParent<T>  {

    public String parentName;
    protected String parentNum;
}
