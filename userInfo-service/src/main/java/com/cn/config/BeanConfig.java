package com.cn.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class BeanConfig {

    // Spring Cloud Nacos 2021 移除了 Ribbon，
    // 在Spring Cloud Commons 项目中添加了 Spring Cloud LoadBalancer 作为新的负载均衡器
    // RandomLoadBalancer ：基于随机访问的负载均衡策略
    // NacosLoadBalancer：基于Nacos集群和权重的负载均衡策略
    // RoundRobinLoadBalancer：基于轮询的负载均衡策略
    // 在不指定的时候默认使用RoundRobinLoadBalancer轮询负载均衡策略。
    // @Bean
    // public ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
    //                                                                LoadBalancerClientFactory loadBalancerClientFactory) {
    //     String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
    //     System.out.println("loadbalancer.client.name ：" + name);
    //     return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    // }

    /**
     * 配置使用naocs官方提供负载均衡规则
     * @param environment 环境变量
     * @param loadBalancerClientFactory 负载均衡客服工厂
     * @param nacosDiscoveryProperties nacos注册配置
     * @return
     */
    @Bean
    public ReactorLoadBalancer<ServiceInstance> nacosLoadBalancer(Environment environment,
                                                                  LoadBalancerClientFactory loadBalancerClientFactory,
                                                                  NacosDiscoveryProperties nacosDiscoveryProperties) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new NacosLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name, nacosDiscoveryProperties);
    }
}
