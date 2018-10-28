package com.rabbit.api.exchange.fanout;

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
public class FanoutProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "fanoutExchange";
        String routeKey = "lll.123";


        String msg = "topics 你好啊！！";

        channel.basicPublish(exchangeName,routeKey,null,msg.getBytes());



    }

}


