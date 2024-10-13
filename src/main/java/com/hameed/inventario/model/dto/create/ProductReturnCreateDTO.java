package com.hameed.inventario.model.dto.create;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReturnCreateDTO {
    private Long customerId;

    private Long productId;

    private int quantityReturned;

    private String reason;
}
