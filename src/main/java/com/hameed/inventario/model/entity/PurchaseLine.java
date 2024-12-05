package com.hameed.inventario.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "po_lines", schema = "inventario-directory")
@Getter
@Setter
public class PurchaseLine extends AbstractEntity {

    @Column(name = "requested_quantity")
    @Positive(message = "Quantity requested cannot be negative or zero")
    @NotNull(message = "Quantity requested cannot be blank")
    private int requestedQuantity;

    @Column(name = "received_quantity")
    private int receivedQuantity = 0;

    @Column(name = "unit_price")
    private Double unitPrice = 0.0;

    @ManyToOne
    @JoinColumn(name = "product_id")
    // no need for managing backed reference since the product entity does not reference its purchases
    private Product product;

    @ManyToOne
    @JoinColumn(name = "po_header_id")
    @NotNull(message = "Purchase Order header for the purchase line cannot be blank")
//    @JsonBackReference
    private PurchaseOrder purchaseOrder;

}
