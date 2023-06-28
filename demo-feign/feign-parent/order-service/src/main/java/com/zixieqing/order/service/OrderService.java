package com.zixieqing.order.service;

import com.zixieqing.order.client.FeignClient;
import com.zixieqing.order.entity.Order;
import com.zixieqing.order.entity.User;
import com.zixieqing.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@description  : 该类功能  order服务
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@Service
public class OrderService {
   /* @Autowired
    private RestTemplate restTemplate;*/

    @Autowired
    private FeignClient feignClient;

    @Autowired
    private OrderMapper orderMapper;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
       /* // 2、远程调用服务的url 此处直接使用服务名，不用ip+port
        // 原因是底层有一个LoadBalancerInterceptor，里面有一个intercept()，后续玩负载均衡Ribbon会看到
        String url = "http://USER-SERVICE/user/" + order.getUserId();
        // 2.1、利用restTemplate调用远程服务，封装成user对象
        User user = restTemplate.getForObject(url, User.class); */

        // 2、使用feign来进行远程调研
        User user = feignClient.findById(order.getUserId());
        // 3、给oder设置user对象值
        order.setUser(user);
        // 4.返回
        return order;
    }
}
