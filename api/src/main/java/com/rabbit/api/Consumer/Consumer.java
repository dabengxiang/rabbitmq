package com.rabbit.api.Consumer;

import com.rabbitmq.client.AMQP;
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
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "rabbit.exchange";
        String exchangeType = "topic";
        String queueName = "exchageQueue";
        String routeKey = "rabbit.*";
        String queueName1 = "myqueue";


        //1.参数是交换机的名字，2.参数是交换机的类型，3.是是否持久化，4.是否自动删除
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,routeKey);


        channel.basicConsume(queueName,true,new MyConsumer(channel));

    }
}
