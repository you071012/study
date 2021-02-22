package com.ukar.study.datasource.annotation;


import com.ukar.study.datasource.enums.DataSourceEnum;

import java.lang.annotation.*;

/**
 * Created by jyou on 2018/9/11.
 *
 * @author jyou
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceAnnotation {
    DataSourceEnum value() default DataSourceEnum.Master;
}
