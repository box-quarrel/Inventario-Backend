package com.hameed.inventario.model.dto;

import com.hameed.inventario.model.entity.SaleItem;
import lombok.Data;

import java.util.Set;

@Data
public class SaleDTO {
    private String salesNumber;
    private Double totalAmount;
    private Double discount;
    private String customerName;
    private Set<SaleItemDTO> saleItems;
}
