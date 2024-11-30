package com.hameed.inventario.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hameed.inventario.enums.DiscountType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SaleRequestDTO {
    private String discountType;
    private Double discountAmount;
    // net and total are calculated in the backend
    private Long customerId;
    private Set<SaleItemRequestDTO> saleItemRequestDTOS;
}
