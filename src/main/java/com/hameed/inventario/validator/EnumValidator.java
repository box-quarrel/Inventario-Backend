package com.hameed.inventario.validator;

import com.hameed.inventario.annotations.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Enum<?>[] enumValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumValues = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true; // If null, the @NotNull annotation should handle it
        }
        for (Enum<?> e : enumValues) {
            if (e.name().equals(s)) {
                return true;
            }
        }
        return false;
    }


}
