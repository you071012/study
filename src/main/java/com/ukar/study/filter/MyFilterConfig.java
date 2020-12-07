package com.ukar.study.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFilterConfig {
    //想配置多个拦截器直接配置多个FilterRegistrationBean即可，其实是一个工厂bean，往里面添加了多个filter

    @Bean
    public FilterRegistrationBean registFilter1() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogFilter2());
        registration.addUrlPatterns("/demo/*");
        registration.setName("logFilter2");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean registFilter2() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogFilter());
        registration.addUrlPatterns("/async/*");
        registration.setName("logFilter");
        registration.setOrder(2);
        return registration;
    }
}
