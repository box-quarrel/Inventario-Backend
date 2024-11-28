package com.hameed.inventario.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleItemRequestDTO {

    private Long productId;

    private int quantity;

    private Double unitPrice;
}
