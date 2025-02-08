package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "cost_history")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CostHistory extends AbstractEntity{

    @Column(name = "old_cost")
    // not negative, will be zero if the product is new
    @PositiveOrZero(message = "Old Cost must be positive or zero")
    private Double oldCost;

    @Column(name = "new_cost")
    @Positive(message = "New Cost must be positive")
    private Double newCost;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
//    @JsonBackReference
    private Product product;
}