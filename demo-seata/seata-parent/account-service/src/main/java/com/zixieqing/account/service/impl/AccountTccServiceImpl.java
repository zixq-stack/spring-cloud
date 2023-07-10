package com.zixieqing.account.service.impl;

import com.zixieqing.account.entity.AccountFreeze;
import com.zixieqing.account.mapper.AccountFreezeMapper;
import com.zixieqing.account.mapper.AccountMapper;
import com.zixieqing.account.service.AccountTccService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 扣款业务
 *
 * <p>@author       : ZiXieqing</p>
 */


public class AccountTccServiceImpl implements AccountTccService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFreezeMapper accountFreezeMapper;

    /**
     * 扣款
     *
     * Try逻辑	资源检查和预留，同时需要判断Cancel是否已经执行，是则拒绝执行本次业务
     *
     * "@TwoPhaseBusinessAction" 中
     * name属性要与当前方法名一致，用于指定Try逻辑对应的方法
     * commitMethod属性值就是confirm逻辑的方法
     * rollbackMethod属性值就是cancel逻辑的方法
     *
     * "@BusinessActionContextParameter" 将指定的参数传递给confirm和cancel
     *
     * @param userId 用户id
     * @param money  要扣的钱
     */
    @Override
    public void deduct(String userId, int money) {
        // RootContext 是seata中的
        String xid = RootContext.getXID();
        AccountFreeze accountFreeze = accountFreezeMapper.selectById(xid);
        // 业务悬挂处理：判断cancel是否已经执行，若执行过则free表中肯定有数据
        if (accountFreeze == null) {
            // 进行扣款
            accountMapper.deduct(userId, money);
            // 记录本次状态
            AccountFreeze freeze = new AccountFreeze();
            freeze.setXid(xid)
                    .setUserId(userId)
                    .setFreezeMoney(money)
                    .setState(AccountFreeze.State.TRY);
            accountFreezeMapper.insert(freeze);
        }
    }

    /**
     * 二阶段confirm确认方法	业务执行和提交		另外需考虑幂等性问题
     * 方法名可以另命名，但需保证与commitMethod一致
     *
     * @param context 上下文，可以传递try方法的参数
     * @return boolean 执行是否成功
     */
    @Override
    public boolean confirm(BusinessActionContext context) {
        // 删掉freeze表中的记录即可  delete方法本身就具有幂等性
        return accountFreezeMapper.deleteById(context.getXid()) == 1;
    }

    /**
     * 二阶段回滚方法	预留资源释放	另外需考虑幂等性问题	需要判断try是否已经执行，否 就需要空回滚
     * 方法名须保证与rollbackMethod一致
     *
     * @param context 上下文，可以传递try方法的参数
     * @return boolean 执行是否成功
     */
    @Override
    public boolean cancel(BusinessActionContext context) {
        // 空回滚处理：判断try是否已经执行
        AccountFreeze freeze = accountFreezeMapper.selectById(context.getXid());
        // 若为null，则try肯定没执行
        if (freeze == null) {
            // 需要进行空回滚
            freeze = new AccountFreeze();
            freeze.setXid(context.getXid())
                    // getActionContext("userId" 的key就是@BusinessActionContextParameter(paramName = "userId")的pramName值
                    .setUserId(context.getActionContext("userId").toString())
                    .setFreezeMoney(0)
                    .setState(AccountFreeze.State.CANCEL);
            return accountFreezeMapper.updateById(freeze) == 1;
        }

        // 幂等性处理
        if (freeze.getState() == AccountFreeze.State.CANCEL) {
            // 说明已经执行过一次cancel了，直接拒绝执行本次业务
            return true;
        }

        // 不为null，则回滚数据
        accountMapper.refund(freeze.getUserId(), freeze.getFreezeMoney());
        // 将冻结金额归0，并修改本次状态
        freeze.setFreezeMoney(0)
                .setState(AccountFreeze.State.CANCEL);
        return accountFreezeMapper.updateById(freeze) == 1;
    }
}
