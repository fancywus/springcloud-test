package com.cn.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    // 开启mybatis查询返回结果集需要实现序列化接口
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String customerName;

    private String customerPhone;

    private String address;

    private String desc;

    private Date createTime;

    private Date updateTime;
}