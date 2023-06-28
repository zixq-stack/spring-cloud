package com.zixieqing.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>@description  : 该类功能  rabbitmq生产者启动类
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootApplication
public class PublisherApp {
    public static void main(String[] args) {
        SpringApplication.run(PublisherApp.class, args);
    }

    // @Bean
    // public Queue msgConverterQueue() {
    //     return new Queue("msg.converter.queue");
    // }
}
