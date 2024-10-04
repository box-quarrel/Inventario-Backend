package com.hameed.inventario.model.dto.update;

import lombok.Data;

@Data
public class POLineDTO {

    private int id;

    private int requestedQuantity;

    private int receivedQuantity;

    private Double unitPrice;

    private ProductDTO product;
}
