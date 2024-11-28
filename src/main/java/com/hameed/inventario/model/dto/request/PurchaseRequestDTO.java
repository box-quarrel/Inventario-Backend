package com.hameed.inventario.model.dto.request;


import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class PurchaseRequestDTO {
    private Double discount;

    private Double totalAmount;
    
    private String notes;

    private Long supplierId;

    private List<POLineRequestDTO> poLineRequestDTOS;
}
