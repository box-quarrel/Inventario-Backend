package com.hameed.inventario.model.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hameed.inventario.annotations.IdMandatory;
import com.hameed.inventario.enums.DiscountType;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class PurchaseRequestDTO {

    private String notes;

    private Long supplierId;

    @UniqueElements(message = "Duplicate purchase line detected")
    @Valid
    private List<POLineRequestDTO> poLineRequestDTOS;
}
