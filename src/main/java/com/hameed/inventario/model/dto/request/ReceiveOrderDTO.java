package com.hameed.inventario.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReceiveOrderDTO {

    private Long purchaseOrderId;

    private List<ReceivedLineDTO> receivedLines;
}
