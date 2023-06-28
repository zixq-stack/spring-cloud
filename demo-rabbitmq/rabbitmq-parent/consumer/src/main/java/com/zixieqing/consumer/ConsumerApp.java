package com.zixieqing.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>@description  : 该类功能  rabbitmq消费者启动类
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootApplication
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }
}
