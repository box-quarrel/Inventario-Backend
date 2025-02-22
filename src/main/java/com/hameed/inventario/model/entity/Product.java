package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Length(min = 2, max = 50, message = "Product Name Length must be between 2 and 50")
    private String productName;

    @Column(name = "code", nullable = false)
    @Length(max = 50, message = "Product Code Length must be less than 50")
    private String productCode;

    @Column(name = "description")
    private String description;

    @Column(name = "barcode")
    // should add validation for a specific format
    private String barcode;

    @Column(name = "current_price")
    @Positive(message = "Current price must be positive")
    private Double currentPrice = 0.0;

    @Column(name = "current_cost")
    @Positive(message = "Current Cost must be positive")
    private Double currentCost = 0.0;

    @Column(name = "quantity", nullable = false)
    @NotNull
    @PositiveOrZero(message = "Product's quantity cannot be a negative value")
    private int quantity = 0;

    @Column(name = "image_url")
    @URL
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
    private Set<Supplier> suppliers = new HashSet<>();

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
