package com.hameed.inventario.model.dto.update;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String categoryName;
    private String categoryCode;
    private String description;
}
