package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hameed.inventario.annotations.ValidEnum;
import com.hameed.inventario.enums.PurchaseStatus;
import com.hameed.inventario.exception.ResourceNotFoundException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "PO_HEADER", schema = "inventario_directory")
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
    @NotBlank
    @ValidEnum(enumClass = PurchaseStatus.class, message = "Invalid purchase status") // this is a custom validator
    private String purchaseStatus;

    // This should be a value for later reference ( e.g. might be used for reporting the amount of discounts provided by a supplier)
    @Column(name = "po_discount")
    @PositiveOrZero(message = "Discount cannot be negative")
    private Double discount = 0.0;

    // This should be the actual total value paid (even after discount)
    @Column(name = "total_amount", nullable = false)
    @PositiveOrZero
    private Double totalAmount = 0.0;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
//    @JsonBackReference
    private Supplier supplier;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JsonManagedReference
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


    public void updatePurchaseLine(PurchaseLine purchaseLine) {
        if (purchaseLine != null && purchaseLine.getId() != null && purchaseLine.getProduct() != null) {
            for (PurchaseLine poLine : purchaseLines) {
                if (poLine.getId() == purchaseLine.getId()) {
                    poLine.setProduct(purchaseLine.getProduct());
                    poLine.setRequestedQuantity(purchaseLine.getRequestedQuantity());
                    return;
                }
            }
        }
        throw new ResourceNotFoundException("Purchase line: " + purchaseLine.getId() + " Could not be found for this purchase order");
    }

    // override the setters and make them private
    private void setTotalAmount(Double totalAmount) {
        if (totalAmount >= 0) {
            this.totalAmount = totalAmount;
        } else {
            throw new DataIntegrityViolationException("Total amount of purchase order cannot be negative");
        }
    }

    public void updateTotalAmount() {
        Double totalAmountOnLines = this.purchaseLines.stream().map(purchaseLine -> purchaseLine.getUnitPrice() * purchaseLine.getReceivedQuantity()).reduce(0.0, Double::sum);
        if (this.discount == null) {
            setDiscount(0.0);
        }
        setTotalAmount(totalAmountOnLines - this.discount);
    }

    @PrePersist
    @PreUpdate
    private void beforeSave() {
        updateTotalAmount();
    }

}
