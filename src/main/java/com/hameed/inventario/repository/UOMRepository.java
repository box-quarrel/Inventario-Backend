package com.hameed.inventario.repository;

import com.hameed.inventario.model.entity.Category;
import com.hameed.inventario.model.entity.UnitOfMeasure;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UOMRepository extends JpaRepository<UnitOfMeasure, Long> {
    public Optional<UnitOfMeasure> findByUomCode(String uomCode);
}
