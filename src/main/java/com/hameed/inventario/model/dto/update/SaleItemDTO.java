package com.hameed.inventario.model.dto.update;

import lombok.Data;

@Data
public class SaleItemDTO {

    private Long id;

    private int quantity;

    private Double unitPrice;

    private ProductDTO product;
}
