package com.hameed.inventario.model.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
public class ResponseDTO<T> {

    private Integer status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from the response
    private T data;

    public ResponseDTO(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
