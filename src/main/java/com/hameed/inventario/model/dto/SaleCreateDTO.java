package com.hameed.inventario.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SaleCreateDTO {
    private Double totalAmount;
    private Double discount;
    private Long customerId;
    private Set<SaleItemDTO> saleItems;
}
