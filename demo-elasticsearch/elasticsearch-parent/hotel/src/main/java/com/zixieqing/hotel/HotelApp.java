package com.zixieqing.hotel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * <p>@author       : ZiXieqing</p>
 */


@SpringBootApplication
@MapperScan("com.zixieqing.hotel.mapper")
public class HotelApp {
    public static void main(String[] args) {
        SpringApplication.run(HotelApp.class, args);
    }
}
