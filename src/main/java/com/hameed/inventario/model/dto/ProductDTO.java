package com.hameed.inventario.model.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String productName;
    private String productCode;
    private String description;
    private String barcode;
    private Double currentPrice;
    private Double currentCost;
    private int quantity;
    private String imageUrl;
    private String categoryName;
    private String primaryUomCode;
}
