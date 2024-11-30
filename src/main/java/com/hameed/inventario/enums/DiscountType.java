package com.hameed.inventario.enums;

import com.hameed.inventario.exception.InvalidDiscountTypeException;

import java.util.Arrays;

public enum DiscountType {
    PERCENTAGE, FIXED;

    public static DiscountType fromString(String type) {
        return Arrays.stream(values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new InvalidDiscountTypeException("Invalid discount type: " + type));
    }
}
