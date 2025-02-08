package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "price_history")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistory extends AbstractEntity{

    @Column(name = "old_price")
    // not negative, will be zero if the product is new
    @PositiveOrZero(message = "Old Price must be positive")
    private Double oldPrice;

    @Column(name = "new_price")
    @Positive(message = "New Price must be positive")
    private Double newPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
//    @JsonBackReference
    private Product product;
}