package com.rabbit.api.deadletter;

import com.rabbit.api.Consumer.MyConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Date:2018/10/30
 * Author: yc.guo the one whom in nengxun
 * Desc:
 */
public class Consumer {


    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        String exchangeName = "normal_exchange";
        String exchangeType = "topic";
        String queueName = "normal_queue";
        String routeKey = "key.*";







        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange","dlx_exchange");

        channel.exchangeDelete(exchangeName,false);
        channel.queueDelete(queueName);



        //1.参数是交换机的名字，2.参数是交换机的类型，3.是是否持久化，4.是否自动删除
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        channel.queueDeclare(queueName,true,false,false,map);

        channel.queueBind(queueName,exchangeName,routeKey);





        channel.exchangeDeclare("dlx_exchange",exchangeType,true,false,null);
        channel.queueDeclare("dlx_queue",true,false,false,null);

        channel.queueBind("dlx_queue","dlx_exchange","#");


        channel.basicConsume(queueName,true,new MyConsumer(channel));

    }



}
