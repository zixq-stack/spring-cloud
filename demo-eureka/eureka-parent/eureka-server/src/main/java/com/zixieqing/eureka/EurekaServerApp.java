package com.zixieqing.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <p>@description  : 该类功能  eureka-server 启动类
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootApplication
@EnableEurekaServer     /*开启eureka server功能*/
public class EurekaServerApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp.class, args);
    }
}
