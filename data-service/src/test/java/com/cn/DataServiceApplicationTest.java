package com.cn;


import com.cn.dao.CustomerMapper;
import com.cn.dao.ScoreMapper;
import com.cn.dao.TblUserMapper;
import com.cn.entity.Score;
import com.cn.entity.TblUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class DataServiceApplicationTest
{
    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private TblUserMapper tblUserMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void name() {
        List<TblUser> tblUsers = tblUserMapper.selectList(null);
        tblUsers.forEach(System.out::println);
    }

    @Test
    public void redisTest() {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set("age", "21岁");
        String age = (String) ops.get("age");
        System.out.println(age);
    }
}
