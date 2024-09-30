package com.hameed.inventario.model.dto;

import com.hameed.inventario.model.entity.Product;
import lombok.Data;

@Data
public class SaleItemDTO {
    private int quantity;

    private Double unitPrice;

    private String productName;
}
