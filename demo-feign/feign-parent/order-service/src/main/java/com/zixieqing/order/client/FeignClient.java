package com.zixieqing.order.client;

import com.zixieqing.order.config.FeignConfig;
import com.zixieqing.order.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>@description  : 该类功能  feign客户端
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@org.springframework.cloud.openfeign.FeignClient(value = "USER-SERVICE",configuration = FeignConfig.class)
/*value 这里的服务名区分大小写
* configuration 针对某个服务进行日志自定义配置     局部配置
* */
public interface FeignClient {
    /**
     * 支持SpringMVC的所有注解
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") long id);
}
