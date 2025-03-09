package com.hameed.inventario.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class PasswordChangeRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String oldPassword;
    private String newPassword;

}
