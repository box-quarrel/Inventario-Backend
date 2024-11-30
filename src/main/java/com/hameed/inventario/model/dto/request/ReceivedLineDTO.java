package com.hameed.inventario.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceivedLineDTO {
    private Long purchaseLineId;
    private int receivedQuantity;
    private Double unitPrice; // this should reflect on the purchase line and then on the product's current cost
}
