package com.hameed.inventario.model.dto.create;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SaleCreateDTO {
    private Double totalAmount;
    private Double discount;
    private Long customerId;
    private Set<SaleItemCreateDTO> saleItemCreateDTOS;
}
