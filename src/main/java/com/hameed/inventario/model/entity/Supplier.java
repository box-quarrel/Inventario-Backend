package com.hameed.inventario.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "suppliers", schema = "inventario_directory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Supplier extends AbstractEntity{
    @Column(name = "name", nullable = false)
    @NotBlank
    @Length(min = 2, max = 50, message = "Supplier Name Length must be between 2 and 50")
    private String supplierName;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "address")
    private String address;

    @ManyToMany(mappedBy = "suppliers", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Product> products;

    // one-to-many relation with poh
    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    @JsonManagedReference
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

    public void removeProduct(Product product) {
        if (product != null) {
            if (products != null && products.remove(product)) {
                product.getSuppliers().remove(this);
            } else {
                throw new IllegalStateException("Product has not been added to the list");
            }
        }
    }


}
