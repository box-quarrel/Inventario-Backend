package com.hameed.inventario.model.dto.update;

import lombok.Data;

import java.util.Set;

@Data
public class SaleDTO {
    private Long id;
    private String salesNumber;
    private Double totalAmount;
    private Double discount;
    private CustomerDTO customer;
    private Set<SaleItemDTO> saleItems;
}