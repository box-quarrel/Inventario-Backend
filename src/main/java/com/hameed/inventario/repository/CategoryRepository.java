package com.hameed.inventario.repository;

import com.hameed.inventario.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findByCategoryCode(String categoryCode);
}
