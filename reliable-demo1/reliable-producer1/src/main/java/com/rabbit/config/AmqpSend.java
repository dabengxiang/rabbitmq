package com.rabbit.config;

import com.rabbit.entity.CommandType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AmqpSend {
    String value();
}
