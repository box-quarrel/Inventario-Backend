package com.hameed.inventario.model.dto;

import lombok.Data;

@Data
public class ProductReturnDTO {

    private String customerName;

    private String productName;

    private int quantityReturned;

    private String reason;
}
