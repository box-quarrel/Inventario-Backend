package com.hameed.inventario.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceivedLineDTO {
    private Long purchaseLineId;
    private int receivedQuantity;
}
