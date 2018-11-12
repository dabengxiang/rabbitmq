package com.rabbit.api.limit;

import com.rabbit.api.Consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * project name : rabbitmq
 * Date:2018/10/29
 * Author: yc.guo
 * DESC:
 */
public class LimitConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.10.114");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "limitExchange";
        String exchangeType = "topic";
        String queueName = "limitQueue";
        String routeKey = "limit.#";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,routeKey);


        //1.message大小   2. 一次给多少数量     3.true: exchange   false:queue 同一个
        channel.basicQos(0,1,false);
        channel.basicConsume(queueName,false,new LimitMyConsumer(channel));
        
        
        
        
    }
    
    
}
