package com.cn.controller;

import com.cn.entity.UserInfo;
import com.cn.service.UserInfoService;
import com.cn.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/query/{id}")
    public R queryUserInfoById(@PathVariable Integer id) {
       return userInfoService.selectUser(id);
    }

    @PostMapping("/add")
    public R addUserInfo(UserInfo userInfo) {
        return userInfoService.registerUser(userInfo);
    }
}
