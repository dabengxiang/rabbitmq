package com.rabbit.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: gyc
 * @Date: 2019/10/29 14:31
 */
@Configuration
@PropertySource("classpath:rabbit-info.properties")
public class InitCmdLine implements CommandLineRunner  {

    @Autowired
    public RabbitTemplate rabbitTemplate;



    @Value("${rabbitmq.reliable1-exchange}")
    public String exChageName;




    @Value("${rabbitmq.routeKey}")
    public String routeKey;

    @Override
    public void run(String... args) throws Exception {
        rabbitTemplate.convertAndSend(exChageName,routeKey,"123");
    }
}
