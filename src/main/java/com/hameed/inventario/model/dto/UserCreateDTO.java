package com.hameed.inventario.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserCreateDTO {
    private String name;
    private String password;
    private Set<String> roles;
}
