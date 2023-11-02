package com.cn.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户类", description = "对应数据用户表的实体类")
public class User {
    @ApiModelProperty(value = "主键id", notes = "主键自增，无须传参")
    private Long id;

    @ApiModelProperty(value = "用户名称", required = true)
    private String username;

    @ApiModelProperty(value = "用户密码", required = true)
    private String password;

    @ApiModelProperty(value = "用户手机号", required = true)
    private String telephone;
}