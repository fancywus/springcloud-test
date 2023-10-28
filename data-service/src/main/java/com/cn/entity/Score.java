package com.cn.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score implements Serializable {

    // 开启mybatis查询返回结果集需要实现序列化接口
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    private Integer sid;

    private String score;

    // private List<TblUser> user = null;
}