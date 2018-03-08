package com.peng;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 过滤器注册类
 * Created by Rukiy on 2017-11-21
 */
@Configuration
public class FilterRegister {

    /**
     * 跨域请求访问
     * @return
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置你要允许的网站域名，如果全允许则设为*


        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new CorsFilter(source));
        // 顺序很重要，避免麻烦设置在最前面
        filterRegistrationBean.setOrder(0);
        return filterRegistrationBean;
    }

    /**
     * druid：WebStatFilter
     * @return
     *//*
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        *//* 过滤规则 *//*
        filterRegistrationBean.addUrlPatterns("*//*");
        *//* 忽略资源 *//*
        filterRegistrationBean.addInitParameter("exclusions",Resources.RESOURCES_PATH);
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    *//**
     * rest性能监测
     * @return
     *//*
    @Bean
    public FilterRegistrationBean restFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new RestFilter());
        *//* 过滤规则 *//*
        filterRegistrationBean.addUrlPatterns("*//*");
        *//* 忽略资源 *//*
        filterRegistrationBean.addInitParameter("exclusions",Resources.RESOURCES_PATH);
        filterRegistrationBean.setOrder(3);
        return filterRegistrationBean;
    }
*/

}
