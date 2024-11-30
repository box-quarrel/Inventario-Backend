package com.hameed.inventario.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReceiveOrderDTO {

    private Long purchaseOrderId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double discount; // this means a discount amount over the whole total amount

    private List<ReceivedLineDTO> receivedLines;
}
