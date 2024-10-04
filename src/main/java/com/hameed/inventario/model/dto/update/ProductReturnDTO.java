package com.hameed.inventario.model.dto.update;

import lombok.Data;

@Data
public class ProductReturnDTO {
    private Long id;

    private CustomerDTO customer;

    private ProductDTO product;

    private int quantityReturned;

    private String reason;
}
