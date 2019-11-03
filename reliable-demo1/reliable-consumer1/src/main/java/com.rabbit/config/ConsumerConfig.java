package com.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: gyc
 * @Date: 2019/10/29 11:02
 */
@Configuration
@PropertySource("classpath:rabbit-info.properties")
public class ConsumerConfig {



    @Value("${rabbitmq.uri}")
    public String rabbitmqUri;

    @Value("${rabbitmq.reliable1-exchange}")
    public String exChageName;


    @Value("${rabbitmq.reliable1-queue}")
    public String queue1Name;


    @Value("${rabbitmq.reliable2-queue}")
    public String queue2Name;


    @Value("${rabbitmq.routeKey}")
    public String routeKey;


    /**
     *
      * @return
     */
    @Bean("RELIABLE-EXCHANGE")
    public DirectExchange exchange(){
        return new DirectExchange(exChageName,true,false);
    }


    @Bean("RELIABLE-QUEUE1")
    public Queue reliableQueue1(){
        return new Queue(queue1Name,true,false,false);
    }


    @Bean("RELIABLE-QUEUE2")
    public Queue reliableQueue2(){
        return new Queue(queue2Name,true,false,false);
    }


    @Bean
    public Binding binding(@Qualifier("RELIABLE-EXCHANGE")DirectExchange exchange,@Qualifier("RELIABLE-QUEUE1")Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(routeKey);

    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }







}
