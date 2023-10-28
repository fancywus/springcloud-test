package com.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient该注解用于向使用consul或者zookeeper作为注册中心时注册服务
// @EnableDiscoveryClient
//启用Eureka 客户端,注册服务端
// @EnableEurekaClient
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}
