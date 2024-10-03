package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_returns", schema = "inventario-directory")
@Getter
@Setter
public class ProductReturn extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity_returned")
    private int quantityReturned;

    @Column(name = "reason")
    private String reason;


}
