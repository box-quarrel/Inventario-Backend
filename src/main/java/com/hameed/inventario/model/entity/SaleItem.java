package com.hameed.inventario.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "sale_lines")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem extends AbstractEntity {

    @Column(name = "quantity")
    @NotNull(message = "Quantity of the sale item cannot be blank")
    @Positive(message = "Quantity of the sale item cannot be zero or negative")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    @NotNull(message = "Sale Header reference cannot be blank for the sale item")
//    @JsonBackReference
    private Sale sale;

}