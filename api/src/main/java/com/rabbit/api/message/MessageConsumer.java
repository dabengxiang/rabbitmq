package com.rabbit.api.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Date:2018/10/28
 * Author:gyc
 * Desc:
 */
public class MessageConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = "messageQueue";


        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.queueDeclare(queueName,true,false,false,null);


        channel.basicConsume(queueName,true,queueingConsumer);



        while (true){

            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            Map<String, Object> headers = delivery.getProperties().getHeaders();
            System.out.println("header is :"+headers.get("abc"));

            System.out.println("msg is :" +new String(delivery.getBody()));


        }


    }
}
