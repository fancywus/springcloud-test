package com.cn.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * GlobalFilter作用等于配置文件中的default-filters，只不过可以自定义业务处理逻辑
 */
// 设置过滤器链的加载顺序
@Order(-1)
@Component
public class MyGatewayGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 这个方法可以拿到请求头并修改请求头
        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header("gatewayKey", "key");
        mutate.header("origin", "fancywu");
        // 将添加gatewayKey放到下游
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }


    /**
     * 简单的全局过滤
     * @param exchange 请求上下文，里面可以获取request和response等信息
     * @param chain 过滤器链，用来把请求委托给下一个过滤器
     * @return Mono<Void> 返回标示当前过滤器业务结束
     */
    // @Override
    // public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    //     // 1.获得一个request请求对象
    //     ServerHttpRequest request = exchange.getRequest();
    //     MultiValueMap<String, String> params = request.getQueryParams();
    //     // 2.从request请求对象中获取authorization参数
    //     String authorization = params.getFirst("authorization");
    //     // 3.判断参数是否匹配
    //     if ("admin".equals(authorization)) {
    //         // 4.是，则放行交由下一个chain继续执行上下文
    //         return chain.filter(exchange);
    //     }
    //     // 5.否,则直接拒绝到下一个chain并设置提示错误状态
    //     ServerHttpResponse response = exchange.getResponse();
    //     response.setStatusCode(HttpStatus.UNAUTHORIZED);
    //     return response.setComplete();
    // }
}
