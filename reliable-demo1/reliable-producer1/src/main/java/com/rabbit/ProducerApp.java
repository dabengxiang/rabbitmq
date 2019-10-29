package com.rabbit;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Author: gyc
 * @Date: 2019/10/29 14:27
 */
@SpringBootApplication
public class ProducerApp {

    public static void main(String[] args) {
//        SpringApplication.run(ProducerApp.class,args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ProducerApp.class);
        builder.web(WebApplicationType.NONE).run(args);
    }
}
