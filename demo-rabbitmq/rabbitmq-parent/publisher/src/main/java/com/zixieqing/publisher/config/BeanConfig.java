package com.zixieqing.publisher.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册bean
 *
 * <p>@author       : ZiXieqing</p>
 */

@Configuration
public class BeanConfig {
    /**
     * 队列声明
     */
    @Bean
    public Queue msgConverterQueue() {
        return new Queue("msg.converter.queue");
    }

    /**
     * 将消息转换器改为jackson序列化方式
     */
/*    @Bean
    public MessageConverter jacksonMsgConverter() {
        return new Jackson2JsonMessageConverter();
    }*/
}
