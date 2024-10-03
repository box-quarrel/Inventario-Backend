package com.hameed.inventario.model.dto;

import lombok.Data;

@Data
public class POLineCreateDTO {
    
    private int requestedQuantity;

    private int receivedQuantity;

    private Double unitPrice;

    private Long productId;
}
