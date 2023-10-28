package com.cn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;

    private Long uid;

    private String pname;

    private BigDecimal price;

    private Integer count;

    private Timestamp buytime;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", uid=" + uid +
                ", pname='" + pname + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", buytime=" + buytime +
                '}';
    }
}