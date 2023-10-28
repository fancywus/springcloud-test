package com.cn.service.impl;

import com.cn.dao.UserMapper;
import com.cn.entity.User;
import com.cn.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public void addUser(User user) {
        userMapper.insertSelective(user);
    }
}
