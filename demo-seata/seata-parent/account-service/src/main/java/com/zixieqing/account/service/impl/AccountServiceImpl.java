package com.zixieqing.account.service.impl;

import com.zixieqing.account.mapper.AccountMapper;
import com.zixieqing.account.service.AccountService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZiXieqing
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @GlobalTransactional
    public void deduct(String userId, int money) {
        log.info("开始扣款");
        try {
            accountMapper.deduct(userId, money);
        } catch (Exception e) {
            throw new RuntimeException("扣款失败，可能是余额不足！", e);
        }
        log.info("扣款成功");
    }
}
