package com.zixieqing.order.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>@description  : 该类功能  user实体类
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -1552488569745674109L;

    private Long id;
    private String username;
    private String address;
}
