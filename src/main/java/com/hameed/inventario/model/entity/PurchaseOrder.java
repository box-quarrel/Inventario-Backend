package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "po_header", schema = "inventoria-directory")
@Getter
@Setter
public class PurchaseOrder extends AbstractEntity{

    // automatically generated
    @Column(name = "po_number")
    private String purchaseNumber;

    @Column(name = "po_status")
    private String purchaseStatus;

    @Column(name = "po_discount")
    private Double discount;

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


}
