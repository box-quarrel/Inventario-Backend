package com.hameed.inventario.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
public class ProductRequestDTO {

    @NotBlank
    @Length(min = 2, max = 255, message = "Product Name Length must be between 2 and 255")
    private String productName;
    @Length(max = 20, message = "Product Code Length must be less than 20")
    private String productCode;
    private String description;
    private String barcode;
    @NotNull
    @Positive(message = "Current price must be positive")
    private Double currentPrice;
    @Positive(message = "Current cost must be positive")
    private Double currentCost;
    @NotNull
    @PositiveOrZero(message = "Product's quantity cannot be a negative value")
    private int quantity;
    @URL
    private String imageUrl;
    private Long categoryId;
    private Long primaryUomId;

}
