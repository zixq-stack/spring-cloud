package com.zixieqing.publisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * <p> mq的ConfirmCallback和ReturnCallback
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class o6PublisherConfirmAndReturnCallbackTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg() {
        CorrelationData correlationData = new CorrelationData();
        // 可以给每条消息设置唯一id  在RabbitConfirmCallback返回失败时可以知道哪个消息失败
        correlationData.setId(UUID.randomUUID().toString().replace("-",""));

        String exchangeName = "publisher.confirm";
        String rountingKey = "com.zixieqing";
        String message = "mq中发布确认模型的ConfirmCallback和ReturnCallback";
        rabbitTemplate.convertAndSend(exchangeName, rountingKey, message, correlationData);

/*        // 发送消息
        this.templateWithConfirmsEnabled
                .convertAndSend(Exchange exchangeName, String routingKey, Object msg, correlationData);

        // 查看是否成功发送到Exchange中
        assertTrue(correlationData.getFuture().get(15, TimeUnit.SECONDS).isAck());*/
    }
}
