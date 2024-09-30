package com.hameed.inventario.model.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductCreateDTO {
    private String productName;
    private String productCode;
    private String description;
    private String barcode;
    private String currentPrice;
    private int quantity;
    private String imageUrl;
    private Long categoryId;
    private Long primaryUomId;

}
