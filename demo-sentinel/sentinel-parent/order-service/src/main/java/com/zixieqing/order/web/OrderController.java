package com.zixieqing.order.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zixieqing.order.pojo.Order;
import com.zixieqing.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @SentinelResource("hot")
    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId,
                                    @RequestHeader(value = "origin",required = false) String origin) {
        System.out.println("origin = " + origin);
        System.out.println("进入了orderservice，当前orderId = " + orderId);

        // 根据id查询订单并返回
        return orderService.queryOrderById(orderId);
    }
}