package com.cn;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.cn.client.UserPointClient;
import com.cn.config.BeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableMethodCache(basePackages = "com.cn.service.impl")
// 在启动类上添加@LoadBalancerClient注解，指定xxx服务使用xxx负载均衡策略。
@LoadBalancerClients(
        @LoadBalancerClient(value = "userPointService", configuration = BeanConfig.class)
)
@EnableFeignClients(basePackageClasses = UserPointClient.class)
public class UserInfoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserInfoServiceApplication.class, args);
    }
}
