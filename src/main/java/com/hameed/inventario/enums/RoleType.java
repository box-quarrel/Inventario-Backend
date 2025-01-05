package com.hameed.inventario.enums;

import com.hameed.inventario.exception.InvalidDiscountTypeException;

import java.util.Arrays;

public enum RoleType {
    ADMIN, USER, EMPLOYEE, MANAGER;

    public static RoleType fromString(String role) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role type: " + role));
    }
}
