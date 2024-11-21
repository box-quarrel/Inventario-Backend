package com.hameed.inventario.model.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from the response
public class ResponseDTO<T> {

    private Integer status;
    private String message;
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
