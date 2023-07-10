package com.zixieqing.feign.config;

import com.zixieqing.feign.fallback.UserClientFallBackFactory;
import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }

    @Bean
    public UserClientFallBackFactory userClientFallBackFactory() {
        return new UserClientFallBackFactory();
    }
}
