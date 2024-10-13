package com.hameed.inventario.model.dto.create;


import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class PurchaseCreateDTO {
    private Double discount;

    private Double totalAmount;
    
    private String notes;

    private Long supplierId;

    private List<POLineCreateDTO> poLineCreateDTOS;
}
