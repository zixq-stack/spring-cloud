package com.zixieqing.storage.mapper;

import com.zixieqing.storage.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author ZiXieqing
 */
public interface StorageMapper extends BaseMapper<Storage> {
    @Update("update storage_tbl set `count` = `count` - #{count} where commodity_code = #{code}")
    int deduct(@Param("code") String commodityCode, @Param("count") int count);
}
