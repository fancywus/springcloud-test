package com.cn.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "enable")
public class ButtonProperties {
    private boolean button;

    public boolean getButton() {
        return button;
    }

    public void setButton(boolean button) {
        this.button = button;
    }
}
