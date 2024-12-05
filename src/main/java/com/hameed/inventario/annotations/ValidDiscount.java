package com.hameed.inventario.annotations;

import com.hameed.inventario.validator.DiscountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DiscountValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDiscount {
    String message() default "If discount type is set, discount value cannot be null or blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
