package com.hameed.inventario.model.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate;
}