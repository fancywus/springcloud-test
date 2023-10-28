package com.cn.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "gateway.routes.config")
@Component
@Data
public class MyGatewayProperties {

    private String dataId;
    private String group;
    private String username;
    private String password;
}
