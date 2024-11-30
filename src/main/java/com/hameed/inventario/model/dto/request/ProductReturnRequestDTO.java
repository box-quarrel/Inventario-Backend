package com.hameed.inventario.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReturnRequestDTO {
    private Long saleId;

    private Long productId;

    private int quantityReturned;

    private String reason;
}
