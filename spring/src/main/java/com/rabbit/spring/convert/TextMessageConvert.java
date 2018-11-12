package com.rabbit.spring.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * project name : rabbitmq
 * Date:2018/11/5
 * Author: yc.guo
 * DESC:
 */
public class TextMessageConvert implements MessageConverter {
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(o.toString().getBytes(),messageProperties);
        
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
//        if(message.getMessageProperties().getContentType().contains("text")){
//            return message.getBody().toString().getBytes();
//        }
        
        return message.getBody();
    
    }
}
