package com.zixieqing;

import com.rabbitmq.client.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>@description  : 该类功能  hello word 简单工作队列模型 消费者测试
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest
public class HelloWordTest {
    private String host = "192.168.46.128";
    private int port = 5672;
    private String username = "zixieqing";
    private String password = "072413";
    private String queueName = "hello-word";

    @Test
    public void consumerTest() throws IOException, TimeoutException {
        // 1、设置链接信息
        ConnectionFactory conFactory = new ConnectionFactory();
        conFactory.setHost(host);
        conFactory.setPort(port);
        conFactory.setUsername(username);
        conFactory.setPassword(password);

        // 2、获取管道
        Channel channel = conFactory.newConnection().createChannel();

        /*
        * 3、队列声明
        * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments);
        * 参数1   队列名
        * 参数2   此队列是否持久化
        * 参数3   此队列是否共享，即：是否让多个消费者共享这个队列中的信息
        * 参数4   此队列是否自动删除，即：最后一个消费者获取信息之后，这个队列是否自动删除
        * 参数5   其他配置项
        *
        * */
        channel.queueDeclare(queueName, false, false, false, null);

        /*
        * 4、订阅消息
        * basicConsume(String queue, boolean autoAck, Consumer callback)
        * 参数1   队列名
        * 参数2   是否自动应答
        * 参数3   回调函数
        * */
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag = " + consumerTag);
                /*
                * 可以获取到交换机、routingkey、deliveryTag
                * */
                System.out.println("envelope = " + envelope);
                System.out.println("properties = " + properties);
                System.out.println("处理了消息：" + new String(body));
            }
        });

        // 这是另外一种接受消息的方式
        /*DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到了消息：" + new String(message.getBody(), StandardCharsets.UTF_8) );
        };

        CancelCallback cancelCallback = consumerTag -> System.out.println("消费者取消了消费信息行为");

        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);*/
    }
}
