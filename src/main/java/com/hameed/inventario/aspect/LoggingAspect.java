package com.hameed.inventario.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public org.springframework.http.ResponseEntity com.hameed.inventario.controller.*.*(..))")
    public void controllerPointcut(){}

    @Pointcut("execution(public * com.hameed.inventario.service.*.*(..))")
    public void servicePointcut(){}

    @Around("controllerPointcut()")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestId = MDC.get("requestId");
        // Log request details
        LOGGER.info("Request ID: {}, Method: {}, URI: {}, IP: {}",
                requestId, request.getMethod(), request.getRequestURI(), request.getRemoteAddr());

        // Proceed with the actual method execution
        Object result = joinPoint.proceed();

        // Log response details
        LOGGER.info("Request ID: {}, Response: {}", requestId, result);

        return result;
    }

    @Around("servicePointcut()")
    public Object logServiceRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        // Capture requestId from MDC
        String requestId = MDC.get("requestId");

        // Log service method entry
        LOGGER.info("Request ID: {}, Invoking Service Method: {} with Args: {}",
                requestId, joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));

        // Proceed with the actual method execution
        Object result = joinPoint.proceed();

        // Log service method exit with response details
        LOGGER.info("Request ID: {}, Service Method Response: {}", requestId, result);

        return result;
    }
}
