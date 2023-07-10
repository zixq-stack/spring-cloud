package com.zixieqing.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.stereotype.Service;

/**
 * Seata之TCC模式实现业务的account接口
 *
 * "@LocalTCC"    SpringCloud + Feign，Feign的调用基于http
 *                此注解所在的接口需要实现TCC的两阶段提交对应方法才行
 *
 * <p>@author       : ZiXieqing</p>
 */

@Service
@LocalTCC
public interface AccountTccService {
    /**
     * 扣款
     *
     * Try逻辑	资源检查和预留，同时需要判断Cancel是否已经执行，是则拒绝执行本次业务
     *
     * "@TwoPhaseBusinessAction" 中
     * 								name属性要与当前方法名一致，用于指定Try逻辑对应的方法
     * 								commitMethod属性值就是confirm逻辑的方法
     * 								rollbackMethod属性值就是cancel逻辑的方法
     *
     * "@BusinessActionContextParameter" 将指定的参数传递给confirm和cancel
     *
     * @param userId 用户id
     * @param money 要扣的钱
     */
    @TwoPhaseBusinessAction(
            name = "deduct",
            commitMethod = "confirm",
            rollbackMethod = "cancel"
    )
    void deduct(@BusinessActionContextParameter(paramName = "userId") String userId,
                @BusinessActionContextParameter(paramName = "money") int money);

    /**
     * 二阶段confirm确认方法	业务执行和提交		另外需考虑幂等性问题
     * 						 方法名可以另命名，但需保证与commitMethod一致
     *
     * @param context 上下文，可以传递try方法的参数
     * @return boolean 执行是否成功
     */
    boolean confirm(BusinessActionContext context);

    /**
     * 二阶段回滚方法	预留资源释放	另外需考虑幂等性问题	需要判断try是否已经执行，否就需要空回滚
     * 				 方法名须保证与rollbackMethod一致
     *
     * @param context 上下文，可以传递try方法的参数
     * @return boolean 执行是否成功
     */
    boolean cancel(BusinessActionContext context);
}
