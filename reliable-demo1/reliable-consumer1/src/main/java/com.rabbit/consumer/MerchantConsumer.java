package com.rabbit.consumer;

import com.rabbit.entity.Merchant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author: gyc
 * @Date: 2019/10/29 16:30
 */
@Component
@RabbitListener(queues = "${rabbitmq.reliable1-queue}")
public class MerchantConsumer  {



    private RestTemplate restTemplate = new RestTemplate();

    @RabbitHandler
    public void process(@Payload String object, @Headers Map<String,Object> headers,
                        Channel channel){
        Long uniqueId = (Long) headers.get("uniqueId");
        System.out.println(" first queue received msg : " + object);
        if(uniqueId!=null){
            System.out.println("http://127.0.0.1:9091/confirm/"+uniqueId);
            restTemplate.postForEntity("http://127.0.0.1:9091/confirm/"+uniqueId,null,String.class);
        }

    }


}
