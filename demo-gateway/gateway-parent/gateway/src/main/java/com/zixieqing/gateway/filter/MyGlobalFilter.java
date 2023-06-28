package com.zixieqing.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>@description  : 该类功能  自定义gateway全局路由器
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@Order(-1)  // 这个注解和本类实现 Ordered 是一样的效果，都是返回一个整数
            // 这个整数表示当前过滤器的执行优先级，越小优先级越高，取值范围就是 int的范围
@Component
public class MyGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求头中的name
        List<String> name = exchange.getRequest().getHeaders().get("name");
        for (String value : name) {
            System.out.println("value = " + value);
            if ("zixieqing".equals(value))
                // 放行
                return chain.filter(exchange);

        }

        // 设置状态码
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        // 不再执行下去，到此结束 setComplete即设置王成的意思
        return exchange.getResponse().setComplete();
    }
}
