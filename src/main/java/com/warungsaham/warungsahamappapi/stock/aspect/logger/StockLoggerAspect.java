package com.warungsaham.warungsahamappapi.stock.aspect.logger;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class StockLoggerAspect {
    
    private static final Logger LOG = LoggerFactory.getLogger(StockLoggerAspect.class);

    @Pointcut("execution(* com.warungsaham.warungsahamappapi.stock.service.StockServiceImpl.*(..))")
    public void stockServiceMethod(){};

    @Before("stockServiceMethod()")
    public void logBeforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toShortString();
        String params = Arrays.toString(joinPoint.getArgs());

        LOG.info("Starting stock method : {} , with parameter : {}",methodName , params);
    }

    @AfterReturning( pointcut = "stockServiceMethod()", returning = "result")
    public void logAfterMethodReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().toShortString();
        String stringResult = result != null ? result.toString() : "NULL";

        LOG.info("Stock method : {} Done, with result : {}",methodName , stringResult);
    }
}
