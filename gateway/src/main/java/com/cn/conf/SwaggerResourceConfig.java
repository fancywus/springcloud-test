package com.cn.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *  创建MySwaggerResourceProvider 类
 *  因为Swagger暂不支持webflux，所以Gateway里不能配置SwaggerConfig，
 *  可以通过实现 SwaggerResourcesProvider方法获取各个服务的Api-doc文档（即SwaggerResources）
 */
@Configuration
@Primary
@Slf4j
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    @Resource
    private RouteLocator routeLocator;

    @Resource
    private GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        // 取出gateway的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        //第一种方式： 结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        for (int i = 0; i < routes.size(); i++) {
            for (int j = 0; j < GatewayRouteInitConfig.routeDefinitionList.size(); j++) {
                if (routes.get(i).equalsIgnoreCase(GatewayRouteInitConfig.routeDefinitionList.get(j).getId())) {
                    RouteDefinition routeDefinition = GatewayRouteInitConfig.routeDefinitionList.get(j);
                    List<PredicateDefinition> predicates = routeDefinition.getPredicates();
                    for (PredicateDefinition predicate : predicates) {
                        resources.add(swaggerResource(routes.get(i), predicate.getArgs().get("pattern")
                                .replace("/**", "/v2/api-docs")));
                    }
                }
            }
        }
        //第二种方式： 结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        // gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
        //         .forEach(route -> {
        //             route.getPredicates().stream().filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
        //                     .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(), predicateDefinition.getArgs()
        //                             .get(NameUtils.GENERATED_NAME_PREFIX + "0")
        //                             .replace("/**", "/v2/api-docs"))));
        //         });
        //第三种方式： 结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        // for (RouteDefinition routeDefinition : GatewayRouteInitConfig.routeDefinitionList) {
        //     // List<PredicateDefinition> predicates = routeDefinition.getPredicates();
        //     // String location = null;
        //     // for (PredicateDefinition predicate : predicates) {
        //     //     String prefix = "/" + routeDefinition.getId() + predicate.getArgs().get("pattern");
        //     //     location = prefix.replace("/**", "/v2/api-docs");
        //     // }
        //     resources.add(swaggerResource(routeDefinition.getId(), "/" + routeDefinition.getId() + "/v2/api-docs"));
        // }
        log.info("List<SwaggerResource>: {}", resources);
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
