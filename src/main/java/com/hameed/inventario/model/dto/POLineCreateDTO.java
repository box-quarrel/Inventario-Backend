package com.hameed.inventario.model.dto;

import lombok.Data;

@Data
public class POLineCreateDTO {
    
    private int quantity;

    private Double unitPrice;

    private Long productId;
}
