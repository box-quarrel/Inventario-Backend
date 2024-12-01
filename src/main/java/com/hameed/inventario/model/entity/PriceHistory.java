package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "price_history", schema = "inventario-directory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistory extends AbstractEntity{

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(name = "new_price")
    private Double newPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
//    @JsonBackReference
    private Product product;
}