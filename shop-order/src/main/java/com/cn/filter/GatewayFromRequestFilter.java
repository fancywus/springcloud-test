package com.cn.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 实现Filter接口，直接拦截访问该模块所有请求，对所有请求的合法性做校验
 */
@Slf4j
@Component
@WebFilter(filterName = "GatewayFromRequestFilter", urlPatterns = "/order/**")
public class GatewayFromRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化GatewayFromRequestFilter... ");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("进入OrderService模块的GatewayFromRequestFilter... ");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String gatewayKey = request.getHeader("gatewayKey");
        if (!StringUtils.hasText(gatewayKey) && !"key".equals(gatewayKey)) {
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("销毁GatewayFromRequestFilter... ");
    }
}
