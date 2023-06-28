package com.zixieqing.publisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p> Topic Exchange 话题模式测试
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class o5TopicExchangeTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMSg2TopicExchangeTest() {
        String exchangeNmae = "topic.exchange";
        String msg = "贫道又升迁了，离目标越来越近了";
        // routing key变为 话题模式 com.zixieqing.blog
        rabbitTemplate.convertAndSend(exchangeNmae, "com.zixieqing.blog", msg);
    }
}
