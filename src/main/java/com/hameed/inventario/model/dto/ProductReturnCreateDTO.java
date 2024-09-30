package com.hameed.inventario.model.dto;

import lombok.Data;

@Data
public class ProductReturnCreateDTO {
    private Long customerId;

    private Long productId;

    private int quantityReturned;

    private String reason;
}
