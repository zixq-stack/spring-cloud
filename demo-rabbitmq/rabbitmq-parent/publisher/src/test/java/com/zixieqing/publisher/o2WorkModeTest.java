package com.zixieqing.publisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>@description  : 该类功能  SpringAMQP测试
 * </p>
 * <p>@author       : ZiXieqing</p>
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class o2WorkModeTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用SpringAMQP实现 hello word 简单队列模式
     */
    @Test
    public void springAMQP2HelloWordTest() {
        // 1、引入spring-boot-starter-springamqp依赖

        // 2、编写application.uml文件

        // 3、发送消息
        String queueName = "hello-word";
        String message = "hello，this is springAMQP";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    /**
     * 使用SpringAMQP实现 work queue 工作队列模式
     */
    @Test
    public void springAMQP2WorkQueueTest() {
        // 1、引入spring-boot-starter-springamqp依赖

        // 2、编写application.uml文件

        // 3、发送消息
        String queueName = "hello-word";
        String message = "hello，this is springAMQP + ";
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
        }
    }
}
