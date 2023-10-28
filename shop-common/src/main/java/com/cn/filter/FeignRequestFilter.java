package com.cn.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 实现RequestInterceptor接口，将请求放入请求头中，往下传递密钥
 * 使用Feign在调用其他服服务的时候,是不通过gateway的,所以是缺少from的header,
 * 所以在进行feign调用的时候需要添加请求头
 */
@SpringBootConfiguration
@Slf4j
public class FeignRequestFilter implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取request请求头信息，传递给下一层
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                requestTemplate.header(name, value);
            }
        }
        // 独立设置参数
        requestTemplate.header("token","tokenKey");
    }
}
