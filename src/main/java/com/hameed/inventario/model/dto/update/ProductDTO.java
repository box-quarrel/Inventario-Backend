package com.hameed.inventario.model.dto.update;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String productName;
    private String productCode;
    private String description;
    private String barcode;
    private Double currentPrice;
    private Double currentCost;
    private int quantity;
    private String imageUrl;
    private CategoryDTO category;
    private UnitOfMeasureDTO primaryUom;
}
