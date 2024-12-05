package com.hameed.inventario.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceivedLineDTO {
    @NotNull(message = "Purchase Line id cannot be blank for the received purchase line")
    private Long purchaseLineId;
    @NotNull(message = "Received Quantities cannot be blank for the received purchase line")
    @PositiveOrZero(message = "Received Quantities cannot be negative")
    private int receivedQuantity;
    @Positive(message = "Unit Price cannot be zero or negative for the received purchase line")
    @NotNull(message = "Unit Price cannot be blank for the received purchase line")
    private Double unitPrice; // this should reflect on the purchase line and then on the product's current cost
}
