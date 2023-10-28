package com.cn.clients;

import com.cn.conf.OrderClientFallbackFactory;
import com.cn.filter.FeignRequestFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 基于Spring Cloud OpenFeign调用微服务Restful接口时，
 * 请求头从A服务传递到B服务，可以使用RequestInterceptor接口传递请求头信息。
 * 使用RequestInterceptor，需实现它的apply(RequestTemplate var1)。
 * 就是在apply方法中向RequestTemplate对象中注入请求头
 */
@FeignClient(value = "orderService",
        configuration = FeignRequestFilter.class,
        fallbackFactory = OrderClientFallbackFactory.class)
public interface OrderClient {

    @GetMapping("/order/get/{pid}")
    String getOrderById(@PathVariable(value = "pid") Long pid);
}
