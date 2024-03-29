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
public class UserLoggerAspect {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserLoggerAspect.class);

    @Pointcut("execution(* com.warungsaham.warungsahamappapi.user.service.*.*(..))")
    public void userServicePackage(){};

    @Pointcut("execution(* com.warungsaham.warungsahamappapi.user.controller.*.*(..))")
    public void userControllerPackage(){};

    @Pointcut("execution(* com.warungsaham.warungsahamappapi.user.dao.*.*(..))")
    public void userDaoPackage(){};

    @Pointcut("userServicePackage() || userControllerPackage() || userDaoPackage()")
    public void userFlow(){};

    @Before("userFlow()")
    public void logBeforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toShortString();
        String params = Arrays.toString(joinPoint.getArgs());

        LOG.info("Starting user method : {} , with parameter : {}",methodName , params);
    }

    @AfterReturning( pointcut = "userFlow()", returning = "result")
    public void logAfterMethodReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().toShortString();
        String stringResult = result != null ? result.toString() : "NULL";

        LOG.info(" user method : {} Done, with result : {}",methodName , stringResult);
    }

}
