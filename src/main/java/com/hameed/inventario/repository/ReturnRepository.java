package com.hameed.inventario.repository;

import com.hameed.inventario.model.entity.ProductReturn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRepository extends JpaRepository<ProductReturn, Long> {
}
