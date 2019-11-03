package com.rabbit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Author: gyc
 * @Date: 2019/10/29 14:27
 */
@SpringBootApplication
@MapperScan(basePackages = "com.rabbit.mapper")
public class ProducerApp {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class,args);
//        SpringApplicationBuilder builder = new SpringApplicationBuilder(ProducerApp.class);
//        builder.web(WebApplicationType.NONE).run(args);
    }
}
