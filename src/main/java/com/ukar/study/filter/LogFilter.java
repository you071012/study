package com.ukar.study.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


/**
 * 使用@WebFilter配置拦截器当前类不能使用 @Component 或 @Configuration，要在启动类上使用 @ServletComponentScan，否则会拦截两次
 * 一次拦截配置的url，一次全部拦截
 *
 * 使用配置类方式没有这个问题，见类：LogFilter2 和 MyFilterConfig
 */
@WebFilter(urlPatterns = {"/async/*"}, filterName = "logFilter")
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("LogFilter Execute cost=" + (System.currentTimeMillis() - start));
    }

    @Override
    public void destroy() {

    }
}
