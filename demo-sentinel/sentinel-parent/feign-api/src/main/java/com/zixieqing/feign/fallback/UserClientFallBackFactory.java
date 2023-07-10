package com.zixieqing.feign.fallback;

import com.zixieqing.feign.clients.UserClient;
import com.zixieqing.feign.pojo.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * userClient失败时的降级处理
 *
 * <p>@author       : ZiXieqing</p>
 */

@Slf4j
public class UserClientFallBackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            /**
             * 重写userClient中的方法，编写失败时的降级逻辑
             */
            @Override
            public User findById(Long id) {
                log.info("userClient的findById()在进行 id = {} 时失败", id);
                return new User();
            }
        };
    }
}
