package com.cn.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cn.clients.OrderClient;
import com.cn.entity.User;
import com.cn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    // @Resource
    // private RestTemplate restTemplate;

    @Resource
    private OrderClient orderClient;

    @GetMapping("/all")
    public String getAll() {
        List<User> users = userService.selectAll();
        return users.toString();
    }

    @PostMapping("/add")
    public String addUser(User user) {
        if (user != null) {
            userService.addUser(user);
            return "添加用户成功";
        }
        return "添加用户失败";
    }

    //SentinelResource定义为一个资源，才可以把热点参数作为一个监控资源，默认只会监控controller
    @SentinelResource("hot")
    @GetMapping("/{pid}")
    public String getOrder(@PathVariable Long pid) {
        // String order = restTemplate.getForObject("http://orderService/order/get/" + pid, String.class);
        // if (pid == 2) {
        //     throw new RuntimeException("故意抛出异常");
        // }
        String order = orderClient.getOrderById(pid);
        return order;
    }
}
