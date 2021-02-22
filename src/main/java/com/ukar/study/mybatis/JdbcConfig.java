package com.ukar.study.mybatis;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JdbcConfig {
    @Value("${druid.masterUrl:jdbc:mysql://localhost:3307/ukar?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true}")
    private String masterUrl;

    @Value("${druid.slave01Url:jdbc:mysql://localhost:3307/ukar01?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true}")
    private String slave01Url;

    @Value("${druid.slave02Url:jdbc:mysql://localhost:3307/ukar02?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true}")
    private String slave02Url;

    @Value("${druid.username:root}")
    private String username;

    @Value("${druid.password:071012}")
    private String password;

    @Value("${druid.driverClass:com.mysql.jdbc.Driver}")
    private String driverClass;

    @Value("${druid.initialSize:5}")
    private int initialSize;

    @Value("${druid.minIdle:5}")
    private int minIdle;

    @Value("${druid.maxActive:60}")
    private int maxActive;

    @Value("${druid.maxWait:10000}")
    private long maxWait;

    @Value("${druid.timeBetweenEvictionRunsMillis:600000}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${druid.minEvictableIdleTimeMillis:300000}")
    private long minEvictableIdleTimeMillis;

    @Value("${druid.keepAlive:true}")
    private boolean keepAlive;

    @Value("${druid.validationQuery:select 1 from dual}")
    private String validationQuery;

    @Value("${druid.testWhileIdle:true}")
    private boolean testWhileIdle;

    @Value("${druid.testOnBorrow:false}")
    private boolean testOnBorrow;

    @Value("${druid.testOnReturn:false}")
    private boolean testOnReturn;

    @Value("${druid.poolPreparedStatements:false}")
    //打开PSCache，并且指定每个连接上PSCache的大小
    private boolean poolPreparedStatements;

    @Value("${druid.maxPoolPreparedStatementPerConnectionSize:20}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${druid.connectionProperties:druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000}")
    //通过connectProperties属性来打开mergeSql功能；慢SQL记录
    private String connectionProperties;

}
