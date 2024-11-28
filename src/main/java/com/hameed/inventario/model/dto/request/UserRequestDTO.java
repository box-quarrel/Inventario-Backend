package com.hameed.inventario.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserRequestDTO {
    private String name;
    private String password;
    private Set<String> roles;
}
