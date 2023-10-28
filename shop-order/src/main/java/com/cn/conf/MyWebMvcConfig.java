package com.cn.conf;

import com.cn.filter.GlobalRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class MyWebMvcConfig extends WebMvcConfigurationSupport {

    @Resource
    private GlobalRequestInterceptor globalRequestInterceptor;

    /**
     * 添加自定义拦截器
     * @param registry InterceptorRegistry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalRequestInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**"); // 放行静态资源
        super.addInterceptors(registry);
    }

    /**
     * 设置默认编码转换器
     * @param converters HttpMessageConverter
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                // 过滤出StringHttpMessageConverter类型实例
                .filter(StringHttpMessageConverter.class::isInstance)
                .map(c -> (StringHttpMessageConverter) c)
                // 这里将转换器的默认编码设置为utf-8
                .forEach(c -> c.setDefaultCharset(StandardCharsets.UTF_8));
    }
}
