package com.cn.conf;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * 动态路由主要实现
 * @PostConstruc 注解的作用，在spring bean的生命周期依赖注入完成后被调用的方法
 */
@Component
@Slf4j
@RefreshScope
public class GatewayRouteInitConfig {

    @Resource
    private MyGatewayProperties myGatewayProperties;

    @Resource
    private NacosConfigProperties nacosConfigProperties;

    @Autowired
    private RouteService routeService;

    /**
     * nacos 配置服务
     */
    @Autowired
    private ConfigService configService;

    /**
     * JSON 转换对象
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        log.info("开始网关动态路由初始化...");
        try {
            String initConfigInfo = configService.getConfigAndSignListener(myGatewayProperties.getDataId(), myGatewayProperties.getGroup(), nacosConfigProperties.getTimeout(), new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String s) {
                    if (StringUtils.hasText(s)) {
                        log.info("接收到网关路由更新配置: \r\n{}", s);
                        List<RouteDefinition> routeDefinitions = null;
                        try {
                            routeDefinitions = objectMapper.readValue(s, new TypeReference<List<RouteDefinition>>() {
                            });
                        } catch (JsonProcessingException e) {
                            log.error("解析路由配置出错，" + e.getMessage(), e);
                        }
                        for (RouteDefinition routeDefinition : Objects.requireNonNull(routeDefinitions)) {
                            routeService.update(routeDefinition);
                        }
                    } else {
                        log.warn("当前网关无动态路由相关配置");
                    }
                }
            });
            log.info("获取网关当前动态路由配置:\r\n{}", initConfigInfo);
            if (StringUtils.hasText(initConfigInfo)) {
                try {
                    List<RouteDefinition> routeDefinitions = objectMapper.readValue(initConfigInfo, new TypeReference<List<RouteDefinition>>() {
                    });
                    for (RouteDefinition routeDefinition : routeDefinitions) {
                        routeService.add(routeDefinition);
                    }
                } catch (JsonProcessingException e) {
                    log.error("解析路由配置出错，" + e.getMessage(), e);
                }
            }
            else {
                log.warn("当前网关无动态路由相关配置");
            }
            log.info("结束网关动态路由初始化...");
        } catch (NacosException e) {
            log.error("初始化网关路由时发生错误", e);
        }
    }
}
