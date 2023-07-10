package com.zixieqing.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
/*    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout.exchange");
    }*/

    /**
     * 定义队列 fanout.queue1
     */
/*    @Bean
    public Queue fanoutExchange4Queue1() {
        return new Queue("fanout.queue1");
    }*/

    /**
     * 将 fanout.exchange 和 fanout.queue1 两个进行绑定
     */
/*    @Bean
    public Binding fanoutExchangeBindQueue1(Queue fanoutExchange4Queue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutExchange4Queue1).to(fanoutExchange);
    }*/

    /**
     * 定义队列 fanout.queue2
     */
/*    @Bean
    public Queue fanoutExchange4Queue2() {
        return new Queue("fanout.queue2");
    }*/

    /**
     * 将 fanout.exchange 和 fanout.queue2 两个进行绑定
     */
/*    @Bean
    public Binding fanoutExchangeBindQueue2(Queue fanoutExchange4Queue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutExchange4Queue2).to(fanoutExchange);
    }*/


    /**
     * TTL正常交换机
     */
    @Bean
    public DirectExchange ttlExchange() {
        return new DirectExchange("ttl.direct", true, false);
    }

    /**
     * TTL正常队列，同时与死信交换机进行绑定
     */
    @Bean
    public Queue ttlQueue() {
        return QueueBuilder
                .durable("ttl.queue")
                // 设置队列的超时时间
                .ttl(10000)
                // 与死信交换机进行绑定
                .deadLetterExchange("dl.direct")
                // 死信交换机与死信队列的routing key
                .deadLetterRoutingKey("dl")
                .build();
    }

    /**
     * 将正常交换机和正常队列进行绑定
     */
    @Bean
    public Binding ttlBinding() {
        return BindingBuilder
                .bind(ttlQueue())
                .to(ttlExchange())
                .with("ttl");
    }

    /**
     * 死信队列：超过队列最大长度
     */
    @Bean
    public Queue queueLength() {
        QueueBuilder.durable("length.queue")
                .maxLength(100)
                .maxLengthBytes(10240)
                .build();

        // 或下面的方式声明

        Map<String, Object> params = new HashMap<>();
        // 队列最大长度，即队列中只能放这么多个消息
        params.put("x-max-length", 100);
        // 队列中最大的字节数
        params.put("x-max-length=bytes", 10240);
        return new Queue("length.queue",false,false,false,params);
    }

    /**
     * 惰性队列声明
     */
    @Bean
    public Queue lazyQueue() {
        Map<String, Object> params = new HashMap();
        params.put("x-queue-mode", "lazy");
        new Queue("lazy.queue", true, true, false, params);

        // 或使用下面更方便的方式

        return QueueBuilder
                .durable("lazy.queue")
                // 声明为惰性队列
                .lazy()
                .build();
    }

    /**
     * 惰性队列：RabbitListener注解的方式 这种就是new一个Map里面放参数的方式
     * @param msg
     */
    // @RabbitListener(queuesToDeclare = @org.springframework.amqp.rabbit.annotation.Queue(
    //         name = "lazy.queue",
    //         durable = "true",
    //         arguments = @Argument(name = "x-queue-mode", value = "lazy")
    // ))
    // public void lazyQueue(String msg) {
    //     System.out.println("消费者接收到了消息：" + msg);
    // }
}
