package com.hameed.inventario.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hameed.inventario.annotations.IdMandatory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@IdMandatory
public class POLineRequestDTO {

    // Purchase Line id should Only be included when a line is updated
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;


    @NotNull(message = "Product on purchase line cannot be blank")
    private Long productId;

    @Positive(message = "Quantity requested cannot be negative or zero")
    @NotNull(message = "Quantity requested cannot be blank")
    private int requestedQuantity;
}
