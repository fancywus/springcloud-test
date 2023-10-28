package com.cn.service;

import com.cn.entity.UserPoint;
import com.cn.utils.R;

public interface UserPointsService {

    UserPoint selectUserPoints(Integer id);

    R addUserPoint(Integer uid, Integer point);

    R pointPay(Integer point);
}
