package com.rabbit.api.returnlistener;

import com.rabbit.api.Consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * project name : rabbitmq
 * Date:2018/10/29
 * Author: yc.guo
 * DESC:
 */
public class Comsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.10.114");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "returnExchange";
        String exchangeType = "topic";
        String queueName = "returnQueue";
        String routeKey = "return.#";

        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,routeKey);


        channel.basicConsume(queueName,true,new MyConsumer(channel));
    }
    
    
}
