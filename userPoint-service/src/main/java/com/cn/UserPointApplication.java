package com.cn;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodCache(basePackages = "com.cn.service.impl")
public class UserPointApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserPointApplication.class, args);
    }
}
