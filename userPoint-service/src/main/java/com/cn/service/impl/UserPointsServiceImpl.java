package com.cn.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.cn.dao.UserPointMapper;
import com.cn.entity.UserPoint;
import com.cn.service.UserPointsService;
import com.cn.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserPointsServiceImpl implements UserPointsService {

    @Resource
    private UserPointMapper userPointMapper;

    @Override
    @Cached(name = "points_cache", cacheType = CacheType.LOCAL, localExpire = 1, timeUnit = TimeUnit.MINUTES)
    public UserPoint selectUserPoints(Integer id) {
        if (id == null) {
            return null;
        }
        UserPoint userPoint = userPointMapper.selectByUserId(id);
        return userPoint;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addUserPoint(Integer uid, Integer point) {
        // 1.判断会员信息
        if (uid == null) {
            return R.fail(40002, "添加会员积分失败，会员信息为空!");
        }
        UserPoint userPoint1 = userPointMapper.selectByUserId(uid);
        if (ObjectUtils.isEmpty(userPoint1)) {
            // 1.1会员在积分表中没有，表示第一次注册添加初始积分20
            UserPoint userPoint = new UserPoint();
            userPoint.setUserid(uid);
            userPoint.setPoints(20);
            userPointMapper.insertSelective(userPoint);
        }
        else {
            // 1.2否则直接修改积分
            userPoint1.setPoints(point);
            userPointMapper.updateByPrimaryKey(userPoint1);
        }
        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R pointPay(Integer point) {
        UserPoint userPoint = userPointMapper.selectByUserId(point);
        userPoint.setPoints(userPoint.getPoints() - 2);
        userPointMapper.updateByUserId(userPoint);
        userPoint = userPointMapper.selectByUserId(point);
        return R.success(userPoint);
    }
}
