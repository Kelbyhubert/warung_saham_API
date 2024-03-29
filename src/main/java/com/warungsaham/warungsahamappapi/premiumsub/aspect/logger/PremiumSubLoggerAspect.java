package com.warungsaham.warungsahamappapi.premiumsub.aspect.logger;

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
public class PremiumSubLoggerAspect {
    
    private static final Logger LOG = LoggerFactory.getLogger(PremiumSubLoggerAspect.class);

    @Pointcut("execution(* com.warungsaham.warungsahamappapi.premiumsub.service.PremiumSubServiceImpl.*(..))")
    public void premiumSubServiceMethod(){};

    @Before("premiumSubServiceMethod()")
    public void logBeforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toShortString();
        String params = Arrays.toString(joinPoint.getArgs());

        LOG.info("Starting Premium Sub method : {} , with parameter : {}",methodName , params);
    }

    @AfterReturning( pointcut = "premiumSubServiceMethod()", returning = "result")
    public void logAfterMethodReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().toShortString();
        String stringResult = result != null ? result.toString() : "NULL";

        LOG.info("Premium Sub method : {} Done, with result : {}",methodName , stringResult);
    }
}
