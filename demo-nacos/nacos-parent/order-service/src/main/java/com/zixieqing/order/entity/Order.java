package com.zixieqing.order.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>@description  : 该类功能  order实体类
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@Data
public class Order implements Serializable {
    private static final long serialVersionUID = -3495691848753718362L;

    private Long id;
    private Long price;
    private String name;
    private Integer num;
    private Long userId;
    private User user;
}
