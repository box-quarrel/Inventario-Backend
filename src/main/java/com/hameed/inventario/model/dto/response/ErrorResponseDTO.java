package com.hameed.inventario.model.dto.response;

import lombok.Data;

@Data
public class ErrorResponseDTO {
    private String errorCode;
    private String errorMessage;
    private String details;
}
