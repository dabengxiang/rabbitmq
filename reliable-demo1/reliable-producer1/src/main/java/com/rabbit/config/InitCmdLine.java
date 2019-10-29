package com.rabbit.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: gyc
 * @Date: 2019/10/29 14:31
 */
@Configuration
public class InitCmdLine implements InitializingBean {

    @Bean
    public RabbitTemplate rabbitTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {


    }
}
