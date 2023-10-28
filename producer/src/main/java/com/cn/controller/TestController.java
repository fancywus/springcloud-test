package com.cn.controller;

import com.cn.conf.ButtonProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("test")
//开启属性更新功能，让这个bean里面的属性会根据配置中心的修改而同步
// @RefreshScope
public class TestController {

    // @Value("${enable.button}")
    // private boolean enable;

    @Resource
    private ButtonProperties buttonProperties;

    // mO9plV3VX0_(

    @GetMapping
    public String getVal() {
        if (buttonProperties.getButton()) {
            return "打开了设置";
        }
        return "关闭了设置";
    }
}
