package com.hameed.inventario.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "suppliers", schema = "inventario-directory")
@Getter
@Setter
public class Supplier extends AbstractEntity{
    @Column(name = "name")
    private String supplierName;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

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
