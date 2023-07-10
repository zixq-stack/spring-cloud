package com.zixieqing.consumer.config;

import org.springframework.context.annotation.Configuration;

/**
 * bean注册
 *
 * <p>@author       : ZiXieqing</p>
 */


@Configuration
public class BeanConfig {
    /**
     * 将消息转换器改为jackson序列化方式
     * @return
     */
/*    @Bean
    public MessageConverter jacksonMsgConverter() {
        return new Jackson2JsonMessageConverter();
    }*/
}