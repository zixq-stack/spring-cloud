package com.zixieqing.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * <p>@description  : 该类功能  Feign自定义配置
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

public class FeignConfig {
    @Bean
    public Logger.Level feignloggingLevel() {
        return Logger.Level.BASIC;
    }
}
