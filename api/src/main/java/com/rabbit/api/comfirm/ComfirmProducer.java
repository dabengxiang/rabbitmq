package com.rabbit.api.comfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Date:2018/10/28
 * Author:gyc
 * Desc:
 */
public class ComfirmProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();



        String exchange = "comfirmExchange";
        String routeKey = "comfirm.a";

        String msg = "hello world";

        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                System.out.println("-----------------Ack----------------");
                System.out.println("l=" + l);
                System.out.println("b=" + b);

            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("-----------------Nack----------------");
                System.out.println("l=" + l);
                System.out.println("b=" + b);

            }
        });
        
        channel.basicPublish(exchange,routeKey,true,null,msg.getBytes());


    }


}
