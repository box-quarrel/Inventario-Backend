package com.hameed.inventario.annotations;

import com.hameed.inventario.validator.IdMandatoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IdMandatoryValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IdMandatory {
    String message() default "Id must be provided for the specified HTTP method.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
