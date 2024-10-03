package com.hameed.inventario.repository;

import com.hameed.inventario.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    public Optional<Sale> findFirstByOrderByIdDesc();
}
