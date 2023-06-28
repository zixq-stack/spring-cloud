package com.zixieqing.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> rabbitMQ配置
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@Configuration
public class RabbitmqConfig {
    /**
     * 定义交换机类型 fanout.exchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout.exchange");
    }

    /**
     * 定义队列 fanout.queue1
     */
    @Bean
    public Queue fanoutExchange4Queue1() {
        return new Queue("fanout.queue1");
    }

    /**
     * 将 fanout.exchange 和 fanout.queue1 两个进行绑定
     */
    @Bean
    public Binding fanoutExchangeBindQueue1(Queue fanoutExchange4Queue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutExchange4Queue1).to(fanoutExchange);
    }

    /**
     * 定义队列 fanout.queue2
     */
    @Bean
    public Queue fanoutExchange4Queue2() {
        return new Queue("fanout.queue2");
    }

    /**
     * 将 fanout.exchange 和 fanout.queue2 两个进行绑定
     */
    @Bean
    public Binding fanoutExchangeBindQueue2(Queue fanoutExchange4Queue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutExchange4Queue2).to(fanoutExchange);
    }
}
