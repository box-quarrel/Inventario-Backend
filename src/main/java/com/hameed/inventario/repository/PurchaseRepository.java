package com.hameed.inventario.repository;

import com.hameed.inventario.model.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseOrder, Long> {
}
