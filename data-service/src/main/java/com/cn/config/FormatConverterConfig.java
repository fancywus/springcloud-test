package com.cn.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * 自定义使用fastjson序列化器覆盖默认的Jackson序列化器，因为使用缓存注解会导致类型转换异常，背后的原因https://segmentfault.com/a/1190000041647471
 * @EnableWebMvc 导致自定义序列化器失效 原因：https://blog.csdn.net/Zong_0915/article/details/126625489
 * 同时使用自定义序列化器，对应的类必须实现序列化
 */
@SpringBootConfiguration
public class FormatConverterConfig {

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }
}
