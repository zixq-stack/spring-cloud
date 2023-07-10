package com.zixieqing.feign.clients;


import com.zixieqing.feign.fallback.UserClientFallBackFactory;
import com.zixieqing.feign.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userservice",fallbackFactory = UserClientFallBackFactory.class)
public interface UserClient {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);
}
