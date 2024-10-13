package com.hameed.inventario.model.dto.update;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReturnDTO {
    private Long id;

    private CustomerDTO customer;

    private ProductDTO product;

    private int quantityReturned;

    private String reason;
}
