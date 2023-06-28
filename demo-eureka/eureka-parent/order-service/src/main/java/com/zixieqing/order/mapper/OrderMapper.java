package com.zixieqing.order.mapper;

import com.zixieqing.order.entity.Order;
import org.apache.ibatis.annotations.Select;

/**
 * <p>@description  : 该类功能  TODO
 * </p>
 * <p>@author       : ZiXieqing</p>
 * <p>@createTime   : 2023-05-28</p>
 */

public interface OrderMapper {
    @Select("select * from tb_order where id = #{id}")
    Order findById(Long id);
}
