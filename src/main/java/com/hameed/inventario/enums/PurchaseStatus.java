package com.hameed.inventario.enums;

import com.hameed.inventario.exception.InvalidDiscountTypeException;

import java.util.Arrays;

public enum PurchaseStatus {
    PENDING, RECEIVED, PARTIALLY_RECEIVED;

    public static PurchaseStatus fromString(String status) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new InvalidDiscountTypeException("Invalid purchase status: " + status));
    }
}
