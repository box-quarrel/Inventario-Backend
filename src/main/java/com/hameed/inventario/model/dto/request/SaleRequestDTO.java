package com.hameed.inventario.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hameed.inventario.annotations.ValidDiscount;
import com.hameed.inventario.annotations.ValidEnum;
import com.hameed.inventario.enums.DiscountType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Set;

@Data
@Builder
@ValidDiscount
public class SaleRequestDTO {

    @ValidEnum(enumClass = DiscountType.class, message = "Invalid discount type")
    private String discountType;

    @PositiveOrZero(message = "Discount value cannot be negative")
    private Double discountValue;
    // net and total are calculated in the backend
    private Long customerId;
    @UniqueElements(message = "Duplicate sale item detected on the same sale")
    @Valid
    private Set<SaleItemRequestDTO> saleItemRequestDTOS;
}
