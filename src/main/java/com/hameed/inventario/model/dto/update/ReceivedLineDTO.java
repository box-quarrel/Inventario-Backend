package com.hameed.inventario.model.dto.update;

import lombok.Data;

@Data
public class ReceivedLineDTO {
    private Long purchaseLineId;
    private int receivedQuantity;
}
