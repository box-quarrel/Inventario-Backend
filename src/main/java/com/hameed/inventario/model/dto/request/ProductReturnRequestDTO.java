package com.hameed.inventario.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReturnRequestDTO {
    @NotNull(message = "Sale to returned from cannot be blank for the Product Return record")
    private Long saleId;

    @NotNull(message = "Product to return cannot be blank for the Product Return record")
    private Long productId;

    @Positive(message = "Quantity to return cannot be negative or zero")
    @NotNull(message = "Quantity to return cannot be blank")
    private int quantityReturned;

    private String reason;
}
