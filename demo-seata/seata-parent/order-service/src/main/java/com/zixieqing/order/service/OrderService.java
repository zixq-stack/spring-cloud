package com.zixieqing.order.service;

import com.zixieqing.order.entity.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    Long create(Order order);
}