package com.zixieqing.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

/**
 * 死信队列测试
 *
 * <p>@author       : ZiXieqing</p>
 */

@Slf4j
@SpringBootTest(classes = PublisherApp.class)
public class o8DelayedQueueTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发消息给TTL正常交换机
     */
    @Test
    void TTLMessageTest() {
        Message message = MessageBuilder
                .withBody("hello,dead-letter-exchange".getBytes(StandardCharsets.UTF_8))
                // 给消息设置失效时间，单位ms
                .setExpiration("5000")
                .build();

        rabbitTemplate.convertAndSend("ttl.direct", "ttl", message);

        log.info("消息发送成功");
    }
}
