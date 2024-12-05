package com.hameed.inventario.validator;

import com.hameed.inventario.annotations.ValidDiscount;
import com.hameed.inventario.enums.DiscountType;
import com.hameed.inventario.model.entity.Sale;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DiscountValidator implements ConstraintValidator<ValidDiscount, Object> {

    // we used reflection to enable reusing the validator over both entity and DTO
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            // Use reflection to get the fields
            Field discountTypeField = value.getClass().getDeclaredField("discountType");
            Field discountValueField = value.getClass().getDeclaredField("discountValue");

            discountTypeField.setAccessible(true);
            discountValueField.setAccessible(true);

            String discountType = (String) discountTypeField.get(value);
            Double discountValue = (Double) discountValueField.get(value);

            // Validation logic
            if (discountType == null) {
                return true; // No discount type set, no further validation needed
            }

            if (!Arrays.stream(DiscountType.values())
                    .map(Enum::name)
                    .anyMatch(type -> type.equalsIgnoreCase(discountType))) {
                return false; // Invalid discount type
            }

            return discountValue != null && discountValue > 0;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Validation failed: fields not found", e);
        }
    }
}
