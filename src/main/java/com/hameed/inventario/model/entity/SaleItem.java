package com.hameed.inventario.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sale_lines", schema = "inventario-directory")
@Getter
@Setter
public class SaleItem extends AbstractEntity {

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    // no need for managing backed reference since the product entity does not reference its purchases
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonBackReference
    private Sale sale;

}