package com.hameed.inventario.model.dto.update;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SaleDTO {
    private Long id;
    private String salesNumber;
    private Double totalAmount;
    private Double discount;
    private CustomerDTO customer;
    private Set<SaleItemDTO> saleItems;
}