package com.cn.service;

import com.cn.entity.User;

import java.util.List;

public interface UserService {

    List<User> selectAll();

    void addUser(User user);
}
