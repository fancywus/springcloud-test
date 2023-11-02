package com.cn.conf;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

// @Component
public class MyWebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 防止如果是springsecurity项目无法访问swagger
     * @param registry
     */
    // @Override
    // protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     // 解决静态资源无法访问
    //     registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    //     // 解决swagger无法访问
    //     registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    //     // 解决swagger的js文件无法访问
    //     registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    // }
}
