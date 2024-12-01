package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products", schema = "inventario-directory")
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
    private UnitOfMeasure primaryUom;

    // many-to-one relation with category
    @ManyToOne
    @JoinColumn(name = "category_code", referencedColumnName = "code")
//    @JsonBackReference
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Product_Supplier",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers;

    // one-to-many relation with historical price/cost recording changes
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
    private List<PriceHistory> priceHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
    private List<CostHistory> costHistoryList = new ArrayList<>();


    public void addSupplier(Supplier supplier) {
        if (supplier != null) {
            if (suppliers == null) {
                suppliers = new HashSet<>();
            }
            suppliers.add(supplier);
            supplier.getProducts().add(this);
        }
    }

    public void removeSupplier(Supplier supplier) {
        if (supplier != null) {
            if (suppliers != null && suppliers.remove(supplier)) {
                supplier.getProducts().remove(this);
            } else {
                throw new IllegalStateException("Supplier has not been added to the list");
            }
        }
    }

    public void setCurrentPrice(Double price) {
        if (price != null) {
            if (!price.equals(currentPrice)) {
                if (priceHistoryList == null) {
                    priceHistoryList = new ArrayList<>();
                }
                PriceHistory priceHistory = PriceHistory.builder().oldPrice(currentPrice).newPrice(price).build();
                priceHistoryList.add(priceHistory);
                priceHistory.setProduct(this);
                currentPrice = price;
            }
        }
    }

    public void setCurrentCost(Double cost) {
        if (cost != null) {
            if (!cost.equals(currentCost)) {
                if (costHistoryList == null) {
                    costHistoryList = new ArrayList<>();
                }
                CostHistory costHistory = CostHistory.builder().oldCost(currentCost).newCost(cost).build();
                costHistoryList.add(costHistory);
                costHistory.setProduct(this);
                currentCost = cost;
            }
        }
    }

}
