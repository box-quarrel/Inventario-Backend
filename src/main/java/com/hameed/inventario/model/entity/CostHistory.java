package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cost_history", schema = "inventario-directory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CostHistory extends AbstractEntity{

    @Column(name = "old_cost")
    private Double oldCost;

    @Column(name = "new_cost")
    private Double newCost;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
//    @JsonBackReference
    private Product product;
}