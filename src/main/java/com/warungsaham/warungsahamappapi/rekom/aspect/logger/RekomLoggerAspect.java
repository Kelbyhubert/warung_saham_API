package com.warungsaham.warungsahamappapi.rekom.aspect.logger;

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
public class RekomLoggerAspect {
    
    private static final Logger LOG = LoggerFactory.getLogger(RekomLoggerAspect.class);

    @Pointcut("execution(* com.warungsaham.warungsahamappapi.rekom.service.RekomServiceImpl.*(..))")
    public void rekomServiceMethod(){};

    @Before("rekomServiceMethod()")
    public void logBeforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toShortString();
        String params = Arrays.toString(joinPoint.getArgs());

        LOG.info("Starting rekom method : {} , with parameter : {}",methodName , params);
    }

    @AfterReturning( pointcut = "rekomServiceMethod()", returning = "result")
    public void logAfterMethodReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().toShortString();
        String stringResult = result != null ? result.toString() : "NULL";

        LOG.info("Rekom method : {} Done, with result : {}",methodName , stringResult);
    }
    
}
