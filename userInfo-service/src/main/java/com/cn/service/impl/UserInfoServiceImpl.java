package com.cn.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.cn.client.UserPointClient;
import com.cn.dao.UserInfoMapper;
import com.cn.entity.UserInfo;
import com.cn.entity.UserPoint;
import com.cn.service.UserInfoService;
import com.cn.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vo.UsersInfoVo;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserPointClient userPointClient;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    @Cached(name = "userInfo_cache", cacheType = CacheType.LOCAL, expire = 1, timeUnit = TimeUnit.MINUTES)
    public R selectUser(Integer id) {
        if (id == null) {
            return R.success(200, "查询不到该用户");
        }
        UserInfo userInfo = userInfoMapper.selectById(id);
        UserPoint userPoint = userPointClient.queryUserInfoById(userInfo.getId());
        UsersInfoVo vo = new UsersInfoVo(userInfo, userPoint);
        return R.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R registerUser(UserInfo user) {
        if (ObjectUtils.isEmpty(user)) {
            return R.fail(40001, "注册信息不能为空!");
        }
        userInfoMapper.insertSelective(user);
        UserInfo userInfo = userInfoMapper.selectByPhone(user.getPhone());
        if (!ObjectUtils.isEmpty(userInfo)) {
            R r = userPointClient.addUserInfo(userInfo.getId(), null);
            if (200 == r.getCode()) {
                return R.success(200, "注册会员成功，已添加20积分");
            }
        }
        return R.fail(40003, "注册会员失败！");
    }
}
