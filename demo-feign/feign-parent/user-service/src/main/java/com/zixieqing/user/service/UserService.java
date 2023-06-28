package com.zixieqing.user.service;

import com.zixieqing.user.entity.User;
import com.zixieqing.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@description  : 该类功能  user服务层
 * </p>
 * <p>@author       : ZiXieqing</p>
 * <p>@createTime   : 2023-05-28</p>
 */

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User queryById(Long id) {
        return userMapper.findById(id);
    }
}
