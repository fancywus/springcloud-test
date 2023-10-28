package com.cn.conf;

import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * 路由配置服务接口
 */
public interface RouteService {

    /**
     * 更新路由配置方法
     * @param routeDefinition RouteDefinition 路由bean定义类
     */
    void update(RouteDefinition routeDefinition);

    /**
     * 添加路由配置
     * @param routeDefinition RouteDefinition 路由bean定义类
     */
    void add(RouteDefinition routeDefinition);
}
