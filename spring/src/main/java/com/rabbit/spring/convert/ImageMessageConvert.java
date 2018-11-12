package com.rabbit.spring.convert;

import ch.qos.logback.core.util.FileUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

/**
 * project name : rabbitmq
 * Date:2018/11/7
 * Author: yc.guo
 * DESC:
 */
public class ImageMessageConvert implements MessageConverter {
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(o.toString().getBytes(),messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String contentType = message.getMessageProperties().getContentType();
        if(contentType.contains("image")){
            Object _extName = message.getMessageProperties().getHeaders().get("extName");
            String extName = _extName == null ? "png" : _extName.toString();           
            File file = new File("C:\\Users\\83673\\" + UUID.randomUUID().toString() + "." + extName);


            try {
                Files.copy(new ByteArrayInputStream(message.getBody()),file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;

        }
        
        return message.getBody();
        
    }
}
