package com.zixieqing.user.mapper;

import com.zixieqing.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>@description  : 该类功能  user持久层
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

public interface UserMapper {
    @Select("select * from tb_user where id = #{id}")
    User findById(@Param("id") Long id);
}
