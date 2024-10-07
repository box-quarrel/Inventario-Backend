package com.hameed.inventario.model.dto.response;

import lombok.Data;

@Data
public class PurchaseResponseDTO {
    private String purchaseNumber;

    public PurchaseResponseDTO(String purchaseNumber){
        this.purchaseNumber = purchaseNumber;
    }
}
