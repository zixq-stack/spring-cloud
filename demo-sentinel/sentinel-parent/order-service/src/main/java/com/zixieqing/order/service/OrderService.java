package com.zixieqing.order.service;

import com.zixieqing.feign.clients.UserClient;
import com.zixieqing.feign.pojo.User;
import com.zixieqing.order.mapper.OrderMapper;
import com.zixieqing.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.用Feign远程调用
        User user = userClient.findById(order.getId());
        // 3.封装user到Order
        order.setUser(user);
        // 4.返回
        return order;
    }
}
