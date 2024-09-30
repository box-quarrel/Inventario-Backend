package com.hameed.inventario.model.dto;

import lombok.Data;

@Data
public class POLineDTO {
    private int quantity;

    private Double unitPrice;

    private String productName;
}
