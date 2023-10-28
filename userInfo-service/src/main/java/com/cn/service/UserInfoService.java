package com.cn.service;

import com.cn.entity.UserInfo;
import com.cn.utils.R;

public interface UserInfoService {

    R selectUser(Integer id);

    R registerUser(UserInfo user);
}
