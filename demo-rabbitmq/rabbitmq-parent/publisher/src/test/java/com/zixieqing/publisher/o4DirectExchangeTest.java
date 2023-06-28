package com.zixieqing.publisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p> DirectEXchange 路由模式测试
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class o4DirectExchangeTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg4DirectExchangeTest() {
        String exchangeNmae = "direct.exchange";
        String message = "this is direct exchange";
        // 把消息发给 routingkey 为zixieqing的队列中
        rabbitTemplate.convertAndSend(exchangeNmae, "zixieqing", message);
    }
}
