package com.cn.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private Boolean enabled;

    private String host;

    private String desc;

    private String title;

    private String version;
}
