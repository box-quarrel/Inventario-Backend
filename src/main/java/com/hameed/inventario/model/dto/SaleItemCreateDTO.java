package com.hameed.inventario.model.dto;

import lombok.Data;

@Data
public class SaleItemCreateDTO {
    private int quantity;

    private Double unitPrice;

    private Long productId;
}
