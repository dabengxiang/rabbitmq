package com.rabbit.consumer;

import com.rabbit.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * project name : rabbitmq
 * Date:2018/11/13
 * Author: yc.guo
 * DESC:
 */
@Component
public class ConsumerRecevier {

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "queue11",durable = "true"),
//            exchange = @Exchange(value = "exchange12221",durable = "true",type = "topic"),
//            key = "springboot.*",
//            ignoreDeclarationExceptions = "true"
//    )
//    )
    @RabbitHandler
    public void OnMessage(Message message, Channel channel) throws IOException {
        Object payload = message.getPayload();
        System.err.println("收到的头部信息是："+message.getHeaders());
        System.err.println("收到的信息是:"+payload);
        Long l = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck( l,false);
    }





    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue11",durable = "true"),
            exchange = @Exchange(value = "exchange11",durable = "true",type = "topic"),
            key = "springboot.*",
            ignoreDeclarationExceptions = "true"
    )
    )
    @RabbitHandler
    public void OnMessageOrder(@Payload Order order, Channel channel, @Headers  Map<String,Object> headers) throws IOException {
        System.err.println("收到的信息是:"+order);
        System.out.println(order.getId());
        System.out.println(order.getName());
        Long l = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck( l,false);
    }
}
