package com.zixieqing.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <p>@description  : 该类功能  RestTemplate配置
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@Configuration
public class RestTemplateConfig {
    /**
     * RestTemplate 用来进行远程调用服务提供方
     * LoadBalanced 注解 是SpringCloud中的
     *              此处作用：赋予RestTemplate负载均衡的能力 也就是在依赖注入时，只注入实例化时被@LoadBalanced修饰的实例
     *              底层是 Spring的Qualifier注解，即为spring的原生操作
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
