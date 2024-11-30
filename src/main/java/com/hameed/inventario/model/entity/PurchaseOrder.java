package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "po_header", schema = "inventario-directory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder extends AbstractEntity{

    // automatically generated
    @Column(name = "po_number")
    private String purchaseNumber;

    @Column(name = "po_status")
    private String purchaseStatus;

    // This should be a value for later reference ( e.g. might be used for reporting the amount of discounts provided by a supplier)
    @Column(name = "po_discount")
    private Double discount;

    // This should be the actual total value paid (even after discount)
    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonBackReference
    private Supplier supplier;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PurchaseLine> purchaseLines = new ArrayList<>();

    public void addPurchaseLine(PurchaseLine purchaseLine) {
        if (purchaseLine != null) {
            if (purchaseLines == null) {
                purchaseLines = new ArrayList<>();
            }

            purchaseLines.add(purchaseLine);
            purchaseLine.setPurchaseOrder(this);
        }
    }

    // override the setters and make them private for totals
    private void setTotalAmount(Double totalAmount) {
        if (totalAmount >= 0) {
            this.totalAmount = totalAmount;
        } else {
            throw new DataIntegrityViolationException("Total amount of purchase order cannot be negative");
        }
    }

    public void updateTotalAmount() {
        Double totalAmountOnLines = purchaseLines.stream().map(PurchaseLine::getUnitPrice).reduce(0.0, Double::sum);
        if (discount == null) {
            setDiscount(0.0);
        }
        this.totalAmount = totalAmountOnLines - discount;
    }

    @PrePersist
    @PreUpdate
    private void beforeSave() {
        updateTotalAmount();
    }

}
