package com.hameed.inventario.model.dto.update;


import lombok.Data;

@Data
public class UnitOfMeasureDTO {
    private Long id;

    private String uomName;

    private String uomCode;

    private String description;
}
