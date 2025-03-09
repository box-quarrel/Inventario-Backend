package com.hameed.inventario.model.dto.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private Set<String> roles;
}
