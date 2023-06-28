package com.zixieqing.user.web;

import com.zixieqing.user.entity.User;
import com.zixieqing.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>@description  : 该类功能  user控制层
 * </p>
 * <p>@author       : ZiXieqing</p>
 * <p>@createTime   : 2023-05-28</p>
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id,
                          @RequestHeader(value = "name",required = false) String name) {
        System.out.println("name = " + name);
        return userService.queryById(id);
    }
}
