package com.rabbit.spring.config;

import com.rabbit.spring.adapt.MessageAdapt;
import com.rabbit.spring.convert.ImageMessageConvert;
import com.rabbit.spring.convert.TextMessageConvert;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * Date:2018/11/4
 * Author:gyc
 * Desc:
 */


@Configuration
@ComponentScan({"com.rabbit.spring.*"})
@Slf4j
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory(){

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;

    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;

    }


//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//        return new RabbitTemplate(connectionFactory);
//    }



    @Bean
    public Queue myqueue001(){
        return new Queue("myqueue001",true);
    }

    @Bean
    public DirectExchange myexchange001(){
        return new DirectExchange("myexchange001",true,false);
    }


    @Bean
    public Binding mybinding001(){
        return  BindingBuilder.bind(queue001()).to(exchange001()).with("routeKey001");
    }



    @Bean
    public Queue myqueue002(){
        return new Queue("myqueue002",false);
    }

    @Bean
    public TopicExchange myexchange002(){
        return new TopicExchange("mysexchange002",false,false);
    }


    @Bean
    public Binding mybinding002(){
        return  BindingBuilder.bind(queue002()).to(exchange002()).with("routeKey.#");
    }



    @Bean
    public TopicExchange exchange001() {
        return new TopicExchange("topic001", true, false);
    }

    @Bean
    public Queue queue001() {
        return new Queue("queue001", true); //队列持久
    }

    @Bean
    public Binding binding001() {
        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
    }

    @Bean
    public TopicExchange exchange002() {
        return new TopicExchange("topic002", true, false);
    }

    @Bean
    public Queue queue002() {
        return new Queue("queue002", true); //队列持久
    }

    @Bean
    public Binding binding002() {
        return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
    }

    @Bean
    public Queue queue003() {
        return new Queue("queue003", true); //队列持久
    }

    @Bean
    public Binding binding003() {
        return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
    }

    @Bean
    public Queue queue_image() {
        return new Queue("image_queue", true); //队列持久
    }

    @Bean
    public Queue queue_pdf() {
        return new Queue("pdf_queue", true); //队列持久
    }



    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(5);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setDefaultRequeueRejected(false);
        container.setExposeListenerChannel(true);

        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String queue) {
                return queue+ UUID.randomUUID().toString();
            }
        });


        container.setChannelAwareMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                log.info("收到的信息是："+new String(message.getBody(),"utf-8"));
                log.info(channel.toString());
            }
        });


        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessageAdapt());


        //        messageListenerAdapter.addQueueOrTagToMethodName("queue001","method1");


        // 设置json的转换
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//
//        messageListenerAdapter.setMessageConverter(new Jackson2JsonMessageConverter());

//        messageListenerAdapter.setMessageConverter(new ImageMessageConvert());

//
//        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();
//
//
//        TextMessageConvert textConvert = new TextMessageConvert();
//
//        converter.addDelegate("text", textConvert);
//        converter.addDelegate("html/text", textConvert);
//        converter.addDelegate("xml/text", textConvert);
//        converter.addDelegate("text/plain", textConvert);
//
//
//        Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
//
//        converter.addDelegate("json", jsonConvert);
//        converter.addDelegate("application/json", jsonConvert);
//
//
//        ImageMessageConvert imageConverter = new ImageMessageConvert();
//        converter.addDelegate("image/png", imageConverter);
//        converter.addDelegate("image", imageConverter);
//
//
//
//        messageListenerAdapter.setMessageConverter(converter);
//
//        container.setMessageListener(messageListenerAdapter);

        return container;



    }


}




