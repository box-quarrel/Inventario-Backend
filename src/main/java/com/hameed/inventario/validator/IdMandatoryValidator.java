package com.hameed.inventario.validator;

import com.hameed.inventario.annotations.IdMandatory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class IdMandatoryValidator implements ConstraintValidator<IdMandatory, Object> {

    private final HttpServletRequest request;

    public IdMandatoryValidator(HttpServletRequest request) {
        this.request = request; // Injected via Spring
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Skip validation for null objects
        }

        // Check HTTP method
        String method = request.getMethod();
        if ("PUT".equalsIgnoreCase(method)) {
            try {
                Field idField = value.getClass().getDeclaredField("id");
                idField.setAccessible(true); // Allow access to private fields
                Object idValue = idField.get(value);
                return idValue != null; // Validate that 'id' is not null
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Skip validation if 'id' field is not present
                return true;
            }
        }
        return true; // Valid for other HTTP methods
    }
}
