package com.zixieqing.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>@description  : 该类功能  hello word 简单队列模型 生产者测试
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@SpringBootTest
public class o1HelloWordTest {
    private String host = "192.168.46.128";
    private int port = 5672;
    private String username = "zixieqing";
    private String password = "072413";
    private String queueName = "hello-word";

    @Test
    public void helloWordTest() throws IOException, TimeoutException {
        // 1、获取管道
        ConnectionFactory conFactory = new ConnectionFactory();
        conFactory.setHost(host);
        conFactory.setPort(port);
        conFactory.setUsername(username);
        conFactory.setPassword(password);

        // 2、获取管道
        Channel channel = conFactory.newConnection().createChannel();
        /*
         * 3、订阅消息
         * basicConsume(String queue, boolean autoAck, Consumer callback)
         * 参数1   队列名
         * 参数2   是否自动应答
         * 参数3   回调函数
         * */
        channel.queueDeclare(queueName, false, false, false, null);

        // 4、消息推送
        String msg = "this is hello word";
        /*
        * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
        * 参数1   交换机名
        * 参数2   路由键
        * 参数3   消息其他配置项
        * 参数4   要发送的消息内容
        * */
        channel.basicPublish("", queueName, null, msg.getBytes());

        // 5、释放资源
        channel.close();
        conFactory.clone();
    }
}
