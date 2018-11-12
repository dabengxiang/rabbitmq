package com.rabbit.spring.adapt;

import com.rabbit.spring.entity.Order;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * project name : rabbitmq
 * Date:2018/11/5
 * Author: yc.guo
 * DESC:
 */
@Slf4j
public class MessageAdapt {

    public void handleMessage(String messageBody){
        log.info(this.getClass().getName()+"-----收到的信息是："+new String(messageBody));

    }


    public void handleMessage(Map messageBody){
        log.info(this.getClass().getName()+"-----Map收到的信息是："+messageBody);

    }


    public void handleMessage(Order order){
        log.info(this.getClass().getName()+"-----order收到的信息是："+order);

    }
    
    
    
    public void handleMessage(byte[] messageBody) {
        System.err.println("默认方法, 消息内容:" + new String(messageBody));
    }

    
    public void method1(String messageBody){
        System.err.println("method1 收到的信息事：" + messageBody);
    }


    public void handleMessage(File file) {
        System.err.println("文件对象 方法, 消息内容:" + file.getName());
    }


}
