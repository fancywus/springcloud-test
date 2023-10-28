package com.cn.conf;

import com.alibaba.fastjson.JSON;
import com.cn.clients.OrderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现FallbackFactory接口，产生异常可以返回兜底方案
 */
@Slf4j
public class OrderClientFallbackFactory implements FallbackFactory<OrderClient> {
    @Override
    public OrderClient create(Throwable ex) {
        return new OrderClient() {
            @Override
            public String getOrderById(Long pid) {
                log.error("捕获该id-{}的异常: {}", pid, ex);
                Map<String, String> map = new HashMap<>();
                map.put("code", "400");
                map.put("msg", ex.getMessage());
                map.put("data", null);
                return JSON.toJSONString(map);
            }
        };
    }
}
