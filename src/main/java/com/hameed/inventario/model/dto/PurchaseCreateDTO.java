package com.hameed.inventario.model.dto;


import lombok.Data;

import java.util.List;


@Data
public class PurchaseCreateDTO {
    private Double discount;

    private Double totalAmount;
    
    private String notes;

    private Long supplierId;

    private List<POLineCreateDTO> poLineCreateDTOS;
}
