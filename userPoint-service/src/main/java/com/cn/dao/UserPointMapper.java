package com.cn.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.entity.UserPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserPointMapper extends BaseMapper<UserPoint> {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPoint record);

    int insertSelective(UserPoint record);

    UserPoint selectByPrimaryKey(Integer id);

    UserPoint selectByUserId(@Param("userid") Integer uid);

    int updateByPrimaryKeySelective(UserPoint record);

    void updateByUserId(UserPoint record);

    int updateByPrimaryKey(UserPoint record);
}