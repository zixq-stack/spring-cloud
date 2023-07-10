package com.zixieqing.publisher.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p> mq的confirmCallback和ReturnCallback
 * </p>
 * <p>@author       : ZiXieqing</p>
 */

@Configuration
public class PublisherConfirmAndReturnConfig implements RabbitTemplate.ConfirmCallback,
        RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 初始化方法
     * 目的：因为ConfirmCallback 和 ReturnCallback这两个接口是RabbitTemplate的内部类
     * 因此：想要让当前编写的PublisherConfirmAndReturnConfig能够访问到这两个接口
     * 那么：就需要把当前类PublisherConfirmAndReturnConfig的confirmCallback 和 returnCallback
     *      注入到RabbitTemplate中去 即：init的作用
     */
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 在前面 publisher-confirm-type: correlated 配置开启的前提下，发布消息成功到exchange后
     *       会进行 ConfirmCallback#confirm 异步回调
     * 参数1、发送消息的ID - correlationData.getID()  和 消息的相关信息
     * 参数2、是否成功发送消息给exchange  true成功；false失败
     * 参数3、失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            System.out.println("消息已经送达到Exchange");
        }else{
            System.out.println("消息没有送达到Exchange");
        }
    }

    /**
     * return要全局唯一，rabbitTemplate中只有一个returnCallback
     *
     * 保证 spring.rabbitmq.template.mandatory = true 的前提下，如果消息未能投递到目标queue中，
     *      触发returnCallback#returnedMessage
     * 参数1、消息 new String(message.getBody())
     * 参数2、消息退回的状态码
     * 参数3、消息退回的原因
     * 参数4、交换机名字
     * 参数5、路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
                                String exchange, String routingKey) {
        System.out.println("消息没有送达到Queue");
    }
}
