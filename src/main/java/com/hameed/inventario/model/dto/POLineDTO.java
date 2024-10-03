package com.hameed.inventario.model.dto;

import lombok.Data;

@Data
public class POLineDTO {

    private int requestedQuantity;

    private int receivedQuantity;

    private Double unitPrice;

    private String productName;
}
