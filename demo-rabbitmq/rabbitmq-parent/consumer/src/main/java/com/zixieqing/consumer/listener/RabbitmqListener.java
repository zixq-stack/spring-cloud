package com.zixieqing.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>@description  : 该类功能  rabbitmq监听
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@Slf4j
@Component
public class RabbitmqListener {
    // 1、导入spring-boot-starter-springamqp依赖

    // 2、配置application.yml

    // 3、编写接受消息逻辑

    /**
     * <p>@description  : 该方法功能 监听 hello-word 队列
     * </p>
     * <p>@methodName   : listenQueue2HelloWord</p>
     * <p>@author: ZiXieqing</p>
     *
     * @param msg 接收到的消息
     */
    // @RabbitListener(queues = "hello-word")
    // public void listenQueue2HelloWord(String msg) {
    //     System.out.println("收到的消息 msg = " + msg);
    // }


    /**
     * <p>@description  : 该方法功能 监听 hello-word 队列
     * </p>
     * <p>@methodName   : listenQueue2HelloWord</p>
     * <p>@author: ZiXieqing</p>
     *
     * @param msg 接收到的消息
     */
/*    @RabbitListener(queues = "hello-word")
    public void listenQueue2WorkQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1收到的消息 msg = " + msg + " + " + LocalTime.now());
        // 模拟性能，假设此消费者性能好
        Thread.sleep(20);
    }*/

    /**
     * <p>@description  : 该方法功能 监听 hello-word 队列
     * </p>
     * <p>@methodName   : listenQueue2HelloWord</p>
     * <p>@author: ZiXieqing</p>
     *
     * @param msg 接收到的消息
     */
/*    @RabbitListener(queues = "hello-word")
    public void listenQueue2WorkQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2.............收到的消息 msg = " + msg + " + " + LocalTime.now());
        // 模拟性能，假设此消费者性差点
        Thread.sleep(200);
    }*/

    /**
     * fanoutExchange模型 监听fanout.queue1 队列的消息
     *
     * @param msg 收到的消息
     */
    // @RabbitListener(queues = "fanout.queue1")
    // public void listenQueue14FanoutExchange(String msg) {
    //     System.out.println("消费者1收到 fanout.queue1 的消息 msg = " + msg);
    // }

    /**
     * fanoutExchange模型 监听fanout.queue1 队列的消息
     *
     * @param msg 收到的消息
     */
    // @RabbitListener(queues = "fanout.queue2")
    // public void listenQueue24FanoutExchange(String msg) {
    //     System.err.println("消费者2收到 fanout.queue2 的消息 msg = " + msg);
    // }

    /**
     * 使用纯注解的方式声明队列、交换机及二者绑定、以及监听此队列的消息
     *
     * @param msg 监听到的消息
     */
    // @RabbitListener(bindings = @QueueBinding(
    //         // 队列声明
    //         value = @Queue(name = "direct.queue1"),
    //         // 交换机声明
    //         exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
    //         // 队列和交换机的绑定键值，是一个数组
    //         key = {"zixieqing"}
    // ))
    // public void listenQueue14DirectExchange(String msg) {
    //     System.err.println("消费者1收到 direct.queue1 的消息 msg = " + msg);
    // }

    /**
     * 使用纯注解的方式声明队列、交换机及二者绑定、以及监听此队列的消息
     *
     * @param msg 监听到的消息
     */
    // @RabbitListener(bindings = @QueueBinding(
    //         // 队列声明
    //         value = @Queue(name = "direct.queue2"),
    //         // 交换机声明
    //         exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
    //         // 队列和交换机的绑定键值，是一个数组
    //         key = {"zimingxuan"}
    // ))
    // public void listenQueue24DirectExchange(String msg) {
    //     System.err.println("消费者2收到 direct.queue2 的消息 msg = " + msg);
    // }


    /**
     * 使用纯注解的方式声明队列、交换机及二者绑定、以及监听此队列的消息
     */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(name = "topic.queue1"),
    //         exchange = @Exchange(name = "topic.exchange", type = ExchangeTypes.TOPIC),
    //         key = {"*.zixieqing.#"}
    // ))
    // public void listenQueue14TopicExchange(String msg) {
    //     System.out.println("消费者1收到 topic.queue1 的消息 msg = " + msg);
    // }

    /**
     * 使用纯注解的方式声明队列、交换机及二者绑定、以及监听此队列的消息
     */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(name = "topic.queue2"),
    //         exchange = @Exchange(name = "topic.exchange", type = ExchangeTypes.TOPIC),
    //         key = {"#.blog"}
    // ))
    // public void listenQueue24TopicExchange(String msg) {
    //     System.err.println("消费者1收到 topic.queue1 的消息 msg = " + msg);
    // }


    /**
     * 使用jackson的方式对消息进行接收
     *
     * @param msg 接收到的消息      注：这里的类型需要和生产者发送消息时的类型保持一致
     */
    // @RabbitListener(queues = "msg.converter.queue")
    // public void listenQueue4Jackson(HashMap<String, Object> msg) {
    //     System.out.println("消费者收到消息 msg = " + msg);
    // }


    /**
     * 监听死信队列：死信交换机+死信队列进行绑定
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dl.queue", durable = "true"),
            exchange = @Exchange(name = "dl.direct"),
            key = "dl"
    ))
    public void listenDlQueue(String msg) {
        log.info("消费者收到了dl.queue的消息：{}", msg);
    }
}
