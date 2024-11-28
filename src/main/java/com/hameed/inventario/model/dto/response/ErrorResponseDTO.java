package com.hameed.inventario.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.net.URI;
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from the response
@Data
@Builder
@AllArgsConstructor
// Follows RFC 9457 - Problem Details for HTTP APIs
public class ErrorResponseDTO {
    private URI type;
    private String title;
    private int status;
    private String detail;
    private URI instance;
    private String timestamp;
}
