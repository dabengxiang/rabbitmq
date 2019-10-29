package com.rabbit.api.limit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Date:2018/10/28
 * Author:gyc
 * Desc:
 */
public class LimitProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();



        String exchange = "limitExchange";
        String routeKey = "limit.a";

        
        String msg = "hello world";



        for (int i = 0; i < 6; i++) {
            channel.basicPublish(exchange,routeKey,true,null,msg.getBytes());
            
        }
        


    }


}
