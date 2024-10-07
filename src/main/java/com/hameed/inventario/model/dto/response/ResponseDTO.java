package com.hameed.inventario.model.dto.response;


import lombok.Data;

@Data
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
