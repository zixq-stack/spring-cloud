package com.zixieqing.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>@description  : 该类功能  order-service启动类
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootApplication
@MapperScan("com.zixieqing.order.mapper")
@EnableFeignClients     /*开启feign客户端功能*/
//@EnableFeignClients(defaultConfiguration = FeignConfig.class)     /*针对此服务消费者要调研的所有服务进行自定义日志级别配置 全局配置*/
public class OrderServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApp.class, args);
    }
}
