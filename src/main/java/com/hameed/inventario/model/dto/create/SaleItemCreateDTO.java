package com.hameed.inventario.model.dto.create;

import lombok.Data;

@Data
public class SaleItemCreateDTO {

    private Long productId;

    private int quantity;

    private Double unitPrice;
}
