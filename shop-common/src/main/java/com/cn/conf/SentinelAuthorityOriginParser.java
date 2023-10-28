package com.cn.conf;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 实现RequestOriginParser接口，通过sentinel黑白名单配置应对放行还是拦截
 * 原理是从header拿到自己定义的origin值
 */
@Component
public class SentinelAuthorityOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getHeader("origin");
        if (StringUtils.hasText(origin) && !"fancywu".equals(origin)) {
            return "illegal";
        }
        return origin;
    }
}
