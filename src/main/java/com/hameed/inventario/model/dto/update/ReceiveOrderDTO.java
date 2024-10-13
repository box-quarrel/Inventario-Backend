package com.hameed.inventario.model.dto.update;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReceiveOrderDTO {

    private Long purchaseOrderId;

    private List<ReceivedLineDTO> receivedLines;
}
