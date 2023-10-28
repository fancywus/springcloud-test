package com.cn.controller;

import com.cn.entity.Order;
import com.cn.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    @Value("${server.port}")
    private String port;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private HttpServletRequest request;

    @GetMapping("/all")
    public String getAll(@RequestHeader(value = "token", required = false) String token) {
        System.out.println("token: " + token);
        List<Order> orders = orderService.getAll();
        return orders.toString();
    }

    @PostMapping("/add")
    public String addOrder(Order order) {
        if (order != null) {
            orderService.addOrder(order);
            return "添加订单成功";
        }
        return "添加订单失败";
    }

    @GetMapping("/get/{pid}")
    public String getOrderById(@PathVariable(value = "pid") Long pid) {
        Order order = orderService.getOrderById(pid);
        System.out.println(port + ": 执行了查询...");
        log.info("token = {}", request.getHeader("token"));
        return order.toString();
    }

    @GetMapping("/{uid}")
    public String getOrder(@PathVariable Integer uid) {
        String user = restTemplate.getForObject("http://localhost:8050/user/all", String.class);
        System.out.println(user);
        System.out.println("uid: " + uid);
        return user;
    }
}
