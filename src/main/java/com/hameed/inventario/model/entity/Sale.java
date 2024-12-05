package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hameed.inventario.annotations.ValidDiscount;
import com.hameed.inventario.annotations.ValidEnum;
import com.hameed.inventario.enums.DiscountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sales", schema = "inventario-directory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidDiscount
public class Sale extends AbstractEntity{

    @Column(name = "sales_number")
    private String salesNumber;

    @Column(name = "net_amount")
    @PositiveOrZero(message = "Sales net amount cannot be negative")
    private Double netAmount;

    @Column(name = "total_amount")
    @PositiveOrZero(message = "Sale Total Amount cannot be negative")
    private Double totalAmount;

    @Column(name = "discount_type")
    @ValidEnum(enumClass = DiscountType.class, message = "Invalid invalid type")
    private String discountType;

    @Column(name = "discount_value")
    @PositiveOrZero(message = "Discount value cannot be negative")
    private Double discountValue;

    @ManyToOne
    @JoinColumn(name = "customer_id")
//    @JsonBackReference
    private Customer customer;


    @OneToMany(mappedBy = "sale",  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JsonManagedReference
    private Set<SaleItem> saleItems = new HashSet<>();

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
    private List<ProductReturn> productReturns = new ArrayList<>();

    public void addSaleItem(SaleItem saleItem) {
        if (saleItem != null) {
            if (saleItems == null) {
                saleItems = new HashSet<>();
            }

            saleItems.add(saleItem);
            saleItem.setSale(this);
        }
    }

    public void addProductReturn(ProductReturn productReturn) {
        if (productReturn != null) {
            if (productReturns == null) {
                productReturns = new ArrayList<>();
            }
            productReturns.add(productReturn);
            productReturn.setSale(this);
        }
    }

    // override the setters and make them private for totals
    private void setTotalAmount(Double totalAmount) {
        if (totalAmount >= 0) {
            this.totalAmount = totalAmount;
        } else {
            throw new DataIntegrityViolationException("Total amount of sale cannot be negative");
        }
    }

    private void setNetAmount(Double netAmount) {
        if (netAmount >= 0) {
            this.netAmount = netAmount;
        } else {
            throw new DataIntegrityViolationException("Net amount cannot be negative");
        }
    }

    // after any update to the sale item quantities, this function should be called
    public void updateSaleTotals() {
        // calculate the total amount
        Double totalAmount = this.getSaleItems().stream().map(saleItem -> saleItem.getProduct().getCurrentPrice() * saleItem.getQuantity()).reduce(0.0, Double::sum);
        this.setTotalAmount(totalAmount);
        // Calculate the net amount based on discount
        if (this.discountValue != null) {
            DiscountType discountType = DiscountType.fromString(this.discountType); // this will throw an invalid discount type exception if failed

            double discountAmount = calculateDiscountAmount(totalAmount, discountValue, discountType);
            this.setNetAmount(totalAmount - discountAmount);
        }
    }

    // Helper method for discount calculation
    private double calculateDiscountAmount(double totalAmount, double discountValue, DiscountType discountType) {
        return discountType == DiscountType.FIXED
                ? discountValue
                : totalAmount * discountValue / 100;
    }

    @PrePersist
    @PreUpdate
    private void beforeSave() {
        updateSaleTotals();
    }

}
