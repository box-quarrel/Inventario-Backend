package com.hameed.inventario.model.dto.update;

import lombok.Data;

import java.util.List;

@Data
public class ReceiveOrderDTO {

    private Long purchaseOrderId;

    private List<ReceivedLineDTO> receivedLines;
}
