package com.ukar.study.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.ukar.study.datasource.DynamicDataSource;
import com.ukar.study.datasource.enums.DataSourceEnum;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.ukar.study.mapper"},
        sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisPlusConfig {

    @Autowired
    private JdbcConfig jdbcConfig;

    @Bean(name = "dataSource")
//    @Primary
    public DataSource dataSource() {

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setDefaultTargetDataSource(dbMaster());
        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        dataSourceMap.put(DataSourceEnum.Master.name(), dbMaster());
        dataSourceMap.put(DataSourceEnum.Slave01.name(), dbSlave01());
        dataSourceMap.put(DataSourceEnum.Slave02.name(), dbSlave02());
        dataSource.setTargetDataSources(dataSourceMap);

        return dataSource;
    }

    /**
     * 写库
     *
     * @return
     */
    private DataSource dbMaster() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcConfig.getDriverClass());
        dataSource.setUsername(jdbcConfig.getUsername());
        dataSource.setPassword(jdbcConfig.getPassword());
        dataSource.setUrl(jdbcConfig.getMasterUrl());
        commonSet(dataSource);
        return dataSource;
    }

    /**
     * 读库1
     *
     * @return
     */
    private DataSource dbSlave01() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcConfig.getDriverClass());
        dataSource.setUsername(jdbcConfig.getUsername());
        dataSource.setPassword(jdbcConfig.getPassword());
        dataSource.setUrl(jdbcConfig.getMasterUrl());
        dataSource.setUrl(jdbcConfig.getSlave01Url());
        commonSet(dataSource);
        return dataSource;
    }

    /**
     * 读库1
     *
     * @return
     */
    private DataSource dbSlave02() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcConfig.getDriverClass());
        dataSource.setUsername(jdbcConfig.getUsername());
        dataSource.setPassword(jdbcConfig.getPassword());
        dataSource.setUrl(jdbcConfig.getMasterUrl());
        dataSource.setUrl(jdbcConfig.getSlave02Url());
        commonSet(dataSource);
        return dataSource;
    }

    private void commonSet(DruidDataSource datasource){
        datasource.setInitialSize(jdbcConfig.getInitialSize());
        datasource.setMinIdle(jdbcConfig.getMinIdle());
        datasource.setMaxActive(jdbcConfig.getMaxActive());
        datasource.setMaxWait(jdbcConfig.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(jdbcConfig.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(jdbcConfig.getMinEvictableIdleTimeMillis());
        datasource.setKeepAlive(jdbcConfig.isKeepAlive());
        datasource.setValidationQuery(jdbcConfig.getValidationQuery());
        datasource.setTestWhileIdle(jdbcConfig.isTestWhileIdle());
        datasource.setTestOnBorrow(jdbcConfig.isTestOnBorrow());
        datasource.setTestOnReturn(jdbcConfig.isTestOnReturn());
        datasource.setPoolPreparedStatements(jdbcConfig.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(jdbcConfig.getMaxPoolPreparedStatementPerConnectionSize());
        datasource.setConnectionProperties(jdbcConfig.getConnectionProperties());
    }


    @Bean(value = "sqlSessionFactory")
//    @Primary
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
        //机器id信息可能要随着机器变更，使用雪花算法生成id，配套entity中id要设置属性 @TableId(value = "id", type = IdType.ASSIGN_ID)
        IdentifierGenerator generator = new DefaultIdentifierGenerator(1,0);
        globalConfig.setIdentifierGenerator(generator);
//        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
//        dbConfig.setIdType(IdType.ASSIGN_ID);
//        globalConfig.setDbConfig(dbConfig);
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
//    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
