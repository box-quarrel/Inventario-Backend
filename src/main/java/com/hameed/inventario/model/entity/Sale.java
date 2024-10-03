package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sales", schema = "inventario-directory")
@Getter
@Setter
public class Sale extends AbstractEntity{

    @Column(name = "sales_number")
    private String salesNumber;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "discount")
    private Double discount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;


    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<SaleItem> saleItems = new HashSet<>();

    public void addSaleItem(SaleItem saleItem) {
        if (saleItem != null) {
            if (saleItems == null) {
                saleItems = new HashSet<>();
            }

            saleItems.add(saleItem);
            saleItem.setSale(this);
        }
    }

}
