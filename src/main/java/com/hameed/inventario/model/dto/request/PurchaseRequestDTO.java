package com.hameed.inventario.model.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hameed.inventario.enums.DiscountType;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class PurchaseRequestDTO {

    private String notes;

    private Long supplierId;

    private List<POLineRequestDTO> poLineRequestDTOS;
}
