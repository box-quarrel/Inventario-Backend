package com.hameed.inventario.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleItemRequestDTO {
    @NotNull(message = "Product id cannot be blank for the sale item")
    private Long productId;

    @NotNull(message = "Quantity of the sale item cannot be blank")
    @Positive(message = "Quantity of the sale item cannot be zero or negative")
    private int quantity;
}
