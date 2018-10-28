package com.rabbit.api.Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Date:2018/10/28
 * Author:gyc
 * Desc:
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();



        String exchange = "rabbit.exchange";
        String routeKey = "rabbit.g.g.x.c";

        String msg = "hello world";

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchange,routeKey,true,null,msg.getBytes());
            //如果exchange不填的话，routekey会匹配到名字为myqueue的队列那里
//            channel.basicPublish("","myqueue",true,null,msg.getBytes());

        }


    }


}
