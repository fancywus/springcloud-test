package com.cn.client;

import com.cn.entity.UserPoint;
import com.cn.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "userPointService")
public interface UserPointClient {

    @GetMapping("point/query/{id}")
    UserPoint queryUserInfoById(@PathVariable("id") Integer id);

    @GetMapping("point/add")
    R addUserInfo(@RequestParam("uid") Integer uid, @RequestParam(value = "point", required = false) Integer point);
}
