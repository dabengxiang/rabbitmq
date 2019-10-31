package com.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: gyc
 * @Date: 2019/10/29 16:30
 */
@Component
@RabbitListener(queues = "${rabbitmq.reliable1-queue}")
public class MerchantConsumer  {



    @RabbitHandler
    public void process(String msg){
        System.out.println(" first queue received msg : " + msg);
    }


}
