package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products", schema = "inventoria-directory")
@Getter
@Setter
public class Product extends AbstractEntity {

    @Column(name = "name")
    private String productName;

    @Column(name = "code")
    private String productCode;

    @Column(name = "description")
    private String description;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "current_cost")
    private Double currentCost;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "image_url")
    private String imageUrl;

    // references primary unit of measure
    @ManyToOne
    @JoinColumn(name = "primary_uom_code", referencedColumnName = "code")
    // no need to manage the back reference since there is no reference for the products list in the unit of measure
    private UnitOfMeasure primaryUom;

    // many-to-one relation with category
    @ManyToOne
    @JoinColumn(name = "category_code", referencedColumnName = "code")
    @JsonBackReference
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "Product_Supplier",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers;

    public void addSupplier(Supplier supplier) {
        if (supplier != null) {
            if (suppliers == null) {
                suppliers = new HashSet<>();
            }
            suppliers.add(supplier);
            supplier.getProducts().add(this);
        }
    }

}
