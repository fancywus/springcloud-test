package com.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userid;

    private Integer points;
}