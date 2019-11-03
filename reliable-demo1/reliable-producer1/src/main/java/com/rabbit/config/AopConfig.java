package com.rabbit.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author gyc
 * @date 2019/11/2
 */

@Aspect
@Component
public class AopConfig {



    @Pointcut(value = "@annotation(amqpSend)",argNames = "amqpSend")
    public void pointcut(AmqpSend amqpSend) {
    }


    @Around(value = "pointcut(amqpSend)",argNames = "joinPoint,amqpSend")
    public Object around(ProceedingJoinPoint joinPoint,AmqpSend amqpSend) throws Throwable {
        Object proceed = joinPoint.proceed();
        String value = amqpSend.value();
        return proceed;
    }
}
