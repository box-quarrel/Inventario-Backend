package com.hameed.inventario.aspect;

import com.hameed.inventario.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;


@Aspect
@Order(2)
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
        String username = SecurityUtil.getUsername();
        // Log request details
        LOGGER.info("Request ID: {}, username: {}, Method: {}, URI: {}, IP: {}",
                requestId, username, request.getMethod(), request.getRequestURI(), request.getRemoteAddr());

        // Proceed with the actual method execution
        Object result = joinPoint.proceed();

        // Log response details
        LOGGER.info("Request ID: {}, username: {}, Response: {}", requestId, username, result);

        return result;
    }

    @Around("servicePointcut()")
    public Object logServiceRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        // Capture requestId from MDC and Security username
        String requestId = MDC.get("requestId");
        String username = SecurityUtil.getUsername();
        // Log service method entry
        LOGGER.info("Request ID: {}, username: {}, Invoking Service Method: {} with Args: {}",
                requestId, username, joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));

        // Proceed with the actual method execution
        Object result = joinPoint.proceed();

        // Log service method exit with response details
        LOGGER.info("Request ID: {}, username: {}, Service Method Response: {}", requestId, username, result);

        return result;
    }
}
