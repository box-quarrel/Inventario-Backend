package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "COST_HISTORY", schema = "inventario_directory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CostHistory extends AbstractEntity{

    @Column(name = "old_cost")
    @Positive(message = "Old Cost must be positive")
    private Double oldCost;

    @Column(name = "new_cost")
    @Positive(message = "New Cost must be positive")
    private Double newCost;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
//    @JsonBackReference
    private Product product;
}