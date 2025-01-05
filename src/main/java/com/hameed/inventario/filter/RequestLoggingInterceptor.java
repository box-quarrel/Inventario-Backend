package com.hameed.inventario.filter;

import com.hameed.inventario.aspect.LoggingAspect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Generate a unique request ID or fetch from the header if provided
        String requestId = UUID.randomUUID().toString();

        // Add request-specific information to MDC
        MDC.put("requestId", requestId);
        LOGGER.info("requestId registered at interception: {}", requestId);
        return true; // Continue processing the request
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Clear MDC after request completion to avoid memory leaks
        MDC.clear();
    }
}

