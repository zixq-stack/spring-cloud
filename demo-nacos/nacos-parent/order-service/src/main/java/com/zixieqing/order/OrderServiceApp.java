package com.zixieqing.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>@description  : 该类功能  order-service启动类
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootApplication
@MapperScan("com.zixieqing.order.mapper")
public class OrderServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApp.class, args);
    }
}
