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


        // 在新版中correlationData具有getFuture，可获取结果，而不用在rabbitTemplate上使用ConfirmCallback
/*        correlationData.getFuture().addCallback( // 对照Ajax
                // 成功
                result -> {
                    // 成功发送到exchange
                    if (result.isAck()) {
                        // 消息发送成功 ack回执
                        System.out.println(correlationData.getId() + " 消息发送成功");
                    } else {    // 未成功发送到exchange
                        // 消息发送失败 nack回执
                        System.out.println(correlationData.getId() + " 消息发送失败，原因：" + result.getReason());
                    }
                }, ex -> { // ex 即 exception   不知道什么原因，抛了异常，没收到MQ的回执
                    System.out.println(correlationData.getId() + " 消息发送失败，原因：" + ex.getMessage());
                }
        );

        rabbitTemplate.convertAndSend(exchangeName, rountingKey, message, correlationData);*/
    }
}
