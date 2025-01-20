package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "product_returns", schema = "inventario_directory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReturn extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "sale_id")
//    @JsonBackReference
    private Sale sale;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Product to return cannot be blank for the Product Return record")
    private Product product;

    @Column(name = "quantity_returned")
    @Positive(message = "Quantity to return cannot be negative or zero")
    @NotNull(message = "Quantity to return cannot be blank")
    private int quantityReturned;

    @Column(name = "reason")
    private String reason;


}
