package com.rabbit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rabbit.entity.RabbitLog;
import com.rabbit.mapper.RabbitLogMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.Correlation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.MessageHeaders;

import org.springframework.stereotype.Component;

/**
 * @author gyc
 * @date 2019/11/2
 */

@Component
@PropertySource("classpath:rabbit-info.properties")
public class RabbitUtil {

    @Value("${rabbitmq.reliable1-exchange}")
    public String exChageName;

    @Value("${rabbitmq.routeKey}")
    public String routeKey;

    @Autowired
    private RabbitLogMapper rabbitLogMapper;



    private static ObjectMapper objectMapper = new ObjectMapper();

    static {

            // 转换为格式化的json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    }



    @Autowired
    private RabbitTemplate rabbitTemplate;




    public void send(Object object,String id,String type ) throws JsonProcessingException {
        RabbitLog rabbitLog = createRabbitLog(id,object,type);
        int insert = rabbitLogMapper.insert(rabbitLog);


        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(rabbitLog.getId().toString());

        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setHeader("type",type);
                messageProperties.setHeader("class",object.getClass());
                messageProperties.setHeader("uniqueId",rabbitLog.getId());


                return message;
            }
        };


//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setHeader("uniqueId",rabbitLog.getId());
//        messageProperties.setHeader("type",rabbitLog.getType());
//        messageProperties.setHeader("class",rabbitLog.getEntityClass());
//        Message message = new Message(rabbitLog.getEntity().getBytes(),messageProperties);
//
//
//        rabbitTemplate.send(exChageName,routeKey,message,correlationData);

        rabbitTemplate.convertAndSend(exChageName,routeKey,rabbitLog.getEntity(),messagePostProcessor,correlationData);


    }


    public RabbitLog createRabbitLog(String entityId,Object  object,String type) throws JsonProcessingException {
        RabbitLog rabbitLog = new RabbitLog();
        String entity = objectMapper.writeValueAsString(object);
        rabbitLog.setEntityId(entityId);
        rabbitLog.setType(type);
        rabbitLog.setEntityClass(object.getClass().getName());
        rabbitLog.setEntity(entity);
        return rabbitLog;
    }

}
