package com.hameed.inventario.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "suppliers", schema = "inventoria-directory")
@Getter
@Setter
public class Supplier extends AbstractEntity{
    @Column(name = "name")
    private String purchaseNumber;

    @Column(name = "contact_name")
    private String purchaseStatus;

    @Column(name = "contact_phone")
    private Double discount;

    @Column(name = "email")
    private Double totalAmount;

    @Column(name = "address")
    private String notes;

    @ManyToMany(mappedBy = "suppliers")
    private Set<Product> products;

    // one-to-many relation with poh
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PurchaseOrder> purchaseOrders;

    public void addProduct(Product product) {
            if (product != null) {
                if (products == null) {
                    products = new HashSet<>();
                }
                products.add(product);
                product.getSuppliers().add(this);
            }
    }


}
