package com.ukar.study.datasource.aspect;

import com.ukar.study.datasource.DataSourceContextHolder;
import com.ukar.study.datasource.annotation.DataSourceAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by jyou on 2018/9/11.
 *
 * @author jyou
 */
@Component
@Aspect
@Order(-999)
public class DataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * 注解类型切点,遇到该注解时会执行切面，切入方法
     *
     * @param point
     * @param dataSourceAnnotation
     * @return
     * @throws Throwable
     */
    @Around("@annotation(dataSourceAnnotation)")
    public Object aroundMethod2(ProceedingJoinPoint point, DataSourceAnnotation dataSourceAnnotation) throws Throwable {
        //切换目标数据源
        String preDataSourcename = DateSourceTypeSingle.getSingle().getDataSourceEnum(dataSourceAnnotation.value());
        DataSourceContextHolder.setJdbcType(preDataSourcename);

        logger.info("方法[{}]切换数据源为 [{}]", point.getSignature(), preDataSourcename);
        Object result = null;
        try {
            result = point.proceed();
            return result;
        } catch (Throwable e) {
            logger.error("service方法调用发生错误: {}", e);
            throw e;
        } finally {
            //恢复主数据源
            DataSourceContextHolder.setMaster();
        }
    }
}
