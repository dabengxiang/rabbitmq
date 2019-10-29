package com.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: gyc
 * @Date: 2019/10/29 16:30
 */
@Component
@RabbitListener(queues = "RELIABLE-QUEUE1")
public class MerchantConsumer  {



}
