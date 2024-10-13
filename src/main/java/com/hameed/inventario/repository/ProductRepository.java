package com.hameed.inventario.repository;

import com.hameed.inventario.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findByProductCode(String  productCode);
}
