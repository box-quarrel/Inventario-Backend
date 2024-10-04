package com.hameed.inventario.model.dto.create;

import lombok.Data;

@Data
public class POLineCreateDTO {

    private Long productId;

    private int requestedQuantity;

    private Double unitPrice;


}
