package com.cn;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// 开启Spring自带缓存功能
// @EnableCaching
// @EnableMethodCache的指定basePackages路径下的缓存才会生效
// https://github.com/alibaba/jetcache/issues/501
@EnableMethodCache(basePackages = "com.cn.service.impl")
public class DataServiceApplication
{
    public static void main(String[] args) {
        SpringApplication.run(DataServiceApplication.class, args);
    }
}
