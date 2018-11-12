package com.rabbit.api.returnlistener;

import com.rabbitmq.client.*;

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
        connectionFactory.setHost("192.168.10.114");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();



        String exchange = "returnExchange";
        String routeKey = "return.a";

        String errorRouteKey = "abc.abc";
        
        String msg = "hello world";

        
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("replyCode:"+replyCode);
                System.err.println("replyText:"+replyText);
                System.err.println("exchange:" + exchange);
                System.err.println("routeKey:" + routeKey);
                System.err.println("properties:" + properties);
                System.err.println("body:" + new String(body));
                System.err.println("-------------------------------------------------");
            }
        });


        for (int i = 0; i < 6; i++) {
            channel.basicPublish(exchange,errorRouteKey,true,null,msg.getBytes());
            
        }
        


    }


}
