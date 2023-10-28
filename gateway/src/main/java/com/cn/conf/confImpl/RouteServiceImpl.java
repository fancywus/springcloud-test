package com.cn.conf.confImpl;

import com.cn.conf.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 实现路由配置方法，同时设置事件发布
 * ApplicationEventPublisherAware是ApplicationContext的父接口之一，
 * 他的功能就是发布事件，也就是把某个事件告诉所有与这个事件相关的监听器
 */
@Component
@Slf4j
public class RouteServiceImpl implements RouteService, ApplicationEventPublisherAware {

    /**
     * 提供了对路由的增加删除等操作
     */
    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 事件发布者
     */
    private ApplicationEventPublisher publisher;

    @Override
    public void update(RouteDefinition routeDefinition) {
        log.info("更新路由配置项: {}", routeDefinition);
        // 根据路由id删除旧的配置项
        this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        // 保存路由最新配置
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        // 通过事件发布者发布刷新路由
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void add(RouteDefinition routeDefinition) {
        log.info("添加路由配置项: {}", routeDefinition);
        // 保存路由最新配置
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        // 通过事件发布者发布刷新路由
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        // 获取事件发布者对象并设置
        this.publisher = applicationEventPublisher;
    }
}
