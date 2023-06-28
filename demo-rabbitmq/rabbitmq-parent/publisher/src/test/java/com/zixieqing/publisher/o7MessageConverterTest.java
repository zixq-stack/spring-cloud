package com.zixieqing.publisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * mq消息转换器测试
 *
 * <p>@author       : ZiXieqing</p>
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class o7MessageConverterTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void mqMSgConverterTest() {
        // 准备消息
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("name", "紫邪情");
        msgMap.put("age", 18);
        msgMap.put("profession", "java");

        // 发送消息
        rabbitTemplate.convertAndSend("msg.converter.queue", msgMap);
    }
}