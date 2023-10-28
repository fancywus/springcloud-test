package com.cn.service.impl;

import com.cn.dao.OrderMapper;
import com.cn.entity.Order;
import com.cn.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<Order> getAll() {
        return orderMapper.selectAll();
    }

    @Override
    public void addOrder(Order order) {
        orderMapper.insertSelective(order);
    }

    @Override
    public Order getOrderById(Long pid) {
        return orderMapper.selectOrderById(pid);
    }
}
