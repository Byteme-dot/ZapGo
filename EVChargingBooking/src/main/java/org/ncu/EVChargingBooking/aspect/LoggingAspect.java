package org.ncu.EVChargingBooking.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* org.ncu.EVChargingBooking.controller..*(..))")
    public void logBeforeRequest( JoinPoint joinPoint) {

        logger.info("================================================");
        logger.info( "METHOD CALLED: {}", joinPoint.getSignature().getName());
        logger.info( "ARGUMENTS: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(

    pointcut ="execution(* org.ncu.EVChargingBooking.controller..*(..))",returning = "result")

    public void logAfterRequest( JoinPoint joinPoint, Object result) {

        logger.info("METHOD COMPLETED: {}", joinPoint.getSignature().getName());
        logger.info("RESPONSE: {}", result);
        logger.info("================================================");
    }

    @AfterThrowing(

    pointcut = "execution(* org.ncu.EVChargingBooking.controller..*(..))", throwing = "exception")

    public void logException(JoinPoint joinPoint, Exception exception) {

        logger.error("EXCEPTION IN METHOD: {}", joinPoint.getSignature().getName());
        logger.error("ERROR MESSAGE: {}", exception.getMessage());
    }
}