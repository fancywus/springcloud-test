package com.cn.conf;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义sentinel发生异常的返回结果
 */
@Component
@Slf4j
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.error("自定义sentinel报告异常信息: {}", e.getMessage());
        String msg = "未知异常";
        Integer code = 429;
        Map<String, Object> map = new HashMap<>();
        if (e instanceof FlowException) {
            msg = "请求访问数量过多，请稍后访问";
        } else if (e instanceof DegradeException) {
            msg = "抱歉，服务发生异常，暂时无法访问";
        } else if (e instanceof ParamFlowException) {
            msg = "该商品访问数量过多，请稍后访问";
        } else if (e instanceof AuthorityException) {
            code = 401;
            msg = "无权访问，非法请求";
        } else {
            code = 404;
        }
        map.put("code", code);
        map.put("msg", msg);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(code);
        response.getWriter().println(JSON.toJSONString(map));
    }
}
