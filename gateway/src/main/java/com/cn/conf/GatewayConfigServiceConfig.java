package com.cn.conf;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * 实例化nacos的ConfigService，交由springbean管理
 * ConfigService 这个类是nacos的分布式配置接口，主要是用来获取配置和添加监听器
 */
@SpringBootConfiguration
public class GatewayConfigServiceConfig {

    @Resource
    private MyGatewayProperties myGatewayProperties;

    @Resource
    private NacosConfigProperties nacosConfigProperties;

    @Bean
    public ConfigService configService() throws NacosException {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, nacosConfigProperties.getServerAddr());
        properties.setProperty(PropertyKeyConst.USERNAME, myGatewayProperties.getUsername());
        properties.setProperty(PropertyKeyConst.PASSWORD, myGatewayProperties.getPassword());
        return NacosFactory.createConfigService(properties);
    }
}
