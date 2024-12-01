package com.hameed.inventario.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "po_lines", schema = "inventario-directory")
@Getter
@Setter
public class PurchaseLine extends AbstractEntity {

    @Column(name = "requested_quantity")
    private int requestedQuantity;

    @Column(name = "received_quantity")
    private int receivedQuantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    // no need for managing backed reference since the product entity does not reference its purchases
    private Product product;

    @ManyToOne
    @JoinColumn(name = "po_header_id")
//    @JsonBackReference
    private PurchaseOrder purchaseOrder;

}
