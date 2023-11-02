package com.cn.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单类", description = "对应数据订单表的实体类")
public class Order {
    @ApiModelProperty(value = "主键id", notes = "主键自增，无须传参")
    private Long id;

    @ApiModelProperty(value = "外键用户表的主键id", required = true)
    private Long uid;

    @ApiModelProperty(value = "商品名称", required = true)
    private String pname;

    @ApiModelProperty(value = "商品价格", required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "购买数量", required = true)
    private Integer count;

    @ApiModelProperty(value = "购买时间", required = true, notes = "yyyy/MM/dd HH:mm:ss")
    private Timestamp buytime;
}