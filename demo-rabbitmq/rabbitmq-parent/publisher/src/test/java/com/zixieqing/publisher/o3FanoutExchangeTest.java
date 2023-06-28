package com.zixieqing.publisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p> fanout exchange 扇形/广播模型测试
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class o3FanoutExchangeTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void fanoutExchange4SendMsgTest() {
        String exchangeName = "fanout.exchange";
        String message = "this is fanout exchange";
        rabbitTemplate.convertAndSend(exchangeName,"",message);
    }
}
