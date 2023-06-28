package com.zixieqing.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>@description  : 该类功能  user实体类
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1659745267802304671L;

    private Long id;
    private String username;
    private String address;
}
