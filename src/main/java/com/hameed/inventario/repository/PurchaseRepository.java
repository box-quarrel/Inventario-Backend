package com.hameed.inventario.repository;

import com.hameed.inventario.model.entity.PurchaseLine;
import com.hameed.inventario.model.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<PurchaseOrder, Long> {
    public Optional<PurchaseOrder> findFirstByOrderByIdDesc();
    public Optional<PurchaseOrder> findByPurchaseNumber(String purchaseNumber);
}
