package com.hameed.inventario.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Order(1)
@Component
public class AuditingAspect {
    // TODO: yet to be implemented

    @Pointcut("execution(public * com.hameed.inventario.mapper.*Mapper.*(..))")
    public void convertToDTOMapperPointcut(){}

    @Around("convertToDTOMapperPointcut()")
    public Object appendAuditingColumns(ProceedingJoinPoint joinPoint) throws Throwable {
        // Proceed with method execution
        Object result = joinPoint.proceed();

        // check if the result is an entity DTO
        if(result != null && isDtoAndAuditable(result)) {
            // TODO: check if user is authorized to see audit fields or not
            removeAuditingFields(result);
        }
        return result;
    }

    private boolean isDtoAndAuditable(Object result){
        Field[] fields = result.getClass().getDeclaredFields();
        return containsColumn(fields, "createdBy") && containsColumn(fields, "lastUpdateBy");
    }

    private boolean containsColumn(Field[] fields, String column) {
        for (Field filed : fields) {
            if (filed.getName().equals(column)) {
                return true;
            }
        }
        return false;
    }

    private void removeAuditingFields(Object result){
        try {
            Field[] fileds = result.getClass().getDeclaredFields();
            for (Field filed : fileds) {
                if(filed.getName().equals("createdBy") || filed.getName().equals("lastUpdateBy")) {
                    filed.setAccessible(true);
                    filed.set(result, null);
                }
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("");
        }
    }

}
