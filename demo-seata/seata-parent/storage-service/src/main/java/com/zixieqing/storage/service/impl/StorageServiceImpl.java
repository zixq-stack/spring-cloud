package com.zixieqing.storage.service.impl;

import com.zixieqing.storage.mapper.StorageMapper;
import com.zixieqing.storage.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZiXieqing
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Override
    @GlobalTransactional
    public void deduct(String commodityCode, int count) {
        log.info("开始扣减库存");
        try {
            storageMapper.deduct(commodityCode, count);
        } catch (Exception e) {
            throw new RuntimeException("扣减库存失败，可能是库存不足！", e);
        }
        log.info("扣减库存成功");
    }
}
