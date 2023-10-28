package com.cn.service;

import com.cn.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    void addOrder(Order order);

    Order getOrderById(Long pid);
}
