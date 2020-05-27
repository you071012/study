package com.ukar.study.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.ukar.study.mapper"},
        sqlSessionFactoryRef = "sqlSessionFactory",
        sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisPlusConfig {

    @Value("${yun.druid.driverClass:com.mysql.jdbc.Driver}")
    private String driverClass;

    @Value("${yun.druid.initialSize:5}")
    private int initialSize;

    @Value("${yun.druid.minIdle:5}")
    private int minIdle;

    @Value("${yun.druid.maxActive:60}")
    private int maxActive;

    @Value("${yun.druid.maxWait:10000}")
    private long maxWait;

    @Value("${yun.druid.timeBetweenEvictionRunsMillis:600000}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${yun.druid.minEvictableIdleTimeMillis:300000}")
    private long minEvictableIdleTimeMillis;

    @Value("${yun.druid.keepAlive:true}")
    private boolean keepAlive;

    @Value("${yun.druid.validationQuery:select 1 from dual}")
    private String validationQuery;

    @Value("${yun.druid.testWhileIdle:true}")
    private boolean testWhileIdle;

    @Value("${yun.druid.testOnBorrow:false}")
    private boolean testOnBorrow;

    @Value("${yun.druid.testOnReturn:false}")
    private boolean testOnReturn;

    @Value("${yun.druid.poolPreparedStatements:false}")
    //打开PSCache，并且指定每个连接上PSCache的大小
    private boolean poolPreparedStatements;

    @Value("${yun.druid.maxPoolPreparedStatementPerConnectionSize:20}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${yun.druid.connectionProperties:druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000}")
    //通过connectProperties属性来打开mergeSql功能；慢SQL记录
    private String connectionProperties;


    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {

        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl("jdbc:mysql://localhost:3306/ukar?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true");
        datasource.setUsername("root");
        datasource.setPassword("12345678");
        datasource.setDriverClassName("com.mysql.jdbc.Driver");

        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setKeepAlive(keepAlive);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }


    @Bean(value = "sqlSessionFactory")
    @Primary
    public MybatisSqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.ukar.study.entity");
        String[] mapperLocations = new String[1];
        mapperLocations[0] = "classpath*:/mapper/*Mapper.xml";
        sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations(mapperLocations));

        //设置分页插件
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{paginationInterceptor});

        //设置驼峰映射
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        GlobalConfig globalConfig = new GlobalConfig();
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);

        return sqlSessionFactoryBean;
    }

    public Resource[] resolveMapperLocations(String[] mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }

    @Bean(value = "transactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("transactionManager") DataSourceTransactionManager transactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager);
        return transactionTemplate;
    }

    @Bean(value = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
