package com.zixieqing.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>@description  : 该类功能  user-service 启动类
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootApplication
@MapperScan("com.zixieqing.user.mapper")
public class UserServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}
