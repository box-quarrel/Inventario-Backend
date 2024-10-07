package com.hameed.inventario.model.dto.response;

import lombok.Data;

@Data
public class SaleResponseDTO {
    private String saleNumber;

    public SaleResponseDTO(String saleNumber) {
        this.saleNumber = saleNumber;
    }
}
