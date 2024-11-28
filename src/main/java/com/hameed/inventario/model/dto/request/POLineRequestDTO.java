package com.hameed.inventario.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class POLineRequestDTO {

    private Long productId;

    private int requestedQuantity;

    private Double unitPrice;


}
