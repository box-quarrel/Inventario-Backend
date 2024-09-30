package com.hameed.inventario.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseDTO {
    private String purchaseNumber;

    private String purchaseStatus;

    private Double discount;

    private Double totalAmount;

    private String notes;

    private Long supplierId;

    private List<POLineDTO> poLineCreateDTOS;
}
