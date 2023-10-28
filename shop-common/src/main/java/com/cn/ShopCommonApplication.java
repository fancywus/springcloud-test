package com.cn;

import com.cn.conf.BeanConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Properties;

@SpringBootApplication
// @EnableDiscoveryClient
// @EnableEurekaClient
// 在启动类上添加@LoadBalancerClient注解，指定xxx服务使用xxx负载均衡策略。
@LoadBalancerClients(
        @LoadBalancerClient(value = "orderService", configuration = BeanConfig.class)
)
// 开启Feign客户端功能，不加此注解默认也开启客户端功能
@EnableFeignClients
@Slf4j
public class ShopCommonApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ShopCommonApplication.class, args);
    }
}
