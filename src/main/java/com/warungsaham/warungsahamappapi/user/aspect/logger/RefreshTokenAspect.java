package com.warungsaham.warungsahamappapi.user.aspect.logger;

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
public class RefreshTokenAspect {
    
    private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenAspect.class);

    @Pointcut("execution(* com.warungsaham.warungsahamappapi.user.service.RefreshTokenServiceImpl.*(..))")
    public void refreshTokenServiceMethod(){};

    @Before("refreshTokenServiceMethod()")
    public void logBeforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toShortString();
        String params = Arrays.toString(joinPoint.getArgs());

        LOG.info("Starting Refresh Token method : {} , with parameter : {}",methodName , params);
    }

    @AfterReturning( pointcut = "refreshTokenServiceMethod()", returning = "result")
    public void logAfterMethodReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().toShortString();
        String stringResult = result != null ? result.toString() : "NULL";

        LOG.info("Refresh Token method : {} Done, with result : {}",methodName , stringResult);
    }


}
