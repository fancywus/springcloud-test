package com.cn.controller;

import com.cn.entity.UserPoint;
import com.cn.service.UserPointsService;
import com.cn.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("point")
public class UserPointController {

    private volatile Set<String> tokenSets = new HashSet<>();

    @Resource
    private UserPointsService userPointsService;

    @GetMapping("token")
    public R getToken() {
        UUID uuid = UUID.randomUUID();
        return R.success(uuid);
    }

    @GetMapping("/pay/{uid}")
    public R pointPay(@RequestHeader("token") String token,
                      @PathVariable Integer uid) {
        if (tokenSets.contains(token)) {
            return R.fail(40005, "重复请求");
        }
        tokenSets.add(token);
        return userPointsService.pointPay(uid);
    }

    @GetMapping("/query/{id}")
    public UserPoint queryUserInfoById(@PathVariable Integer id) {
       return userPointsService.selectUserPoints(id);
    }

    @GetMapping("/add")
    public R addUserInfo(@RequestParam("uid") Integer uid, @RequestParam(value = "point", required = false) Integer point) {
        return userPointsService.addUserPoint(uid, point);
    }
}
