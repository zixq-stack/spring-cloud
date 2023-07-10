package com.zixieqing.order.intercepter;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截请求，允许从什么地方来的请求才能访问此微服务
 *
 * <p>@author       : ZiXieqing</p>
 */

@Component
public class RequestInterceptor implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        // 获取请求中的请求头 可自定义
        String origin = request.getHeader("origin");
        if (StringUtils.isEmpty(origin)) {
            origin = "black";
        }

        return origin;
    }
}