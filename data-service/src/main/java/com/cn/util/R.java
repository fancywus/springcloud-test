package com.cn.util;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应体，实现序列化，才可以使用自定义序列化机制
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    //@JSONField(ordinal = x)来强制规定顺序，其中x是排列顺序号。
    @JSONField(ordinal = 1)
    private Integer code;

    @JSONField(ordinal = 2)
    private String msg;

    @JSONField(ordinal = 3)
    private T data;

    public static <T> R<T> success() {
        R<T> r = new R<>();
        r.code = 200;
        r.msg = "success";
        return r;
    }

    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.code = 200;
        r.msg = "success";
        r.data = data;
        return r;
    }

    public static <T> R<T> fail() {
        R<T> r = new R<>();
        r.code = 404;
        r.msg = "fail";
        return r;
    }

    public static <T> R<T> fail(T data) {
        R<T> r = new R<>();
        r.code = 404;
        r.msg = "fail";
        r.data = data;
        return r;
    }
}
