package com.rabbit.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Date:2018/10/28
 * Author:gyc
 * Desc:
 */
public class MessageProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();



        String queueName = "messageQueue";
        String msg = "msg 的测试。。";


        Map<String,Object> header = new HashMap();

        header.put("abc","123");


        //deliverMode：2 持久化
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties()
                                .builder().deliveryMode(2)
                                        .contentEncoding("utf-8")
                                        .expiration("10000")
                                        .headers(header).build();;








        channel.basicPublish("",queueName,true,basicProperties,msg.getBytes());



    }

}
