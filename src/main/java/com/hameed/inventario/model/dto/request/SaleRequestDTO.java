package com.hameed.inventario.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SaleRequestDTO {
    private Double totalAmount;
    private Double discount;
    private Long customerId;
    private Set<SaleItemRequestDTO> saleItemRequestDTOS;
}
