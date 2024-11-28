package com.hameed.inventario.service;


import com.hameed.inventario.model.dto.basic.UnitOfMeasureDTO;
import com.hameed.inventario.model.entity.UnitOfMeasure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnitOfMeasureService {
    // Create a new unitOfMeasure
    public UnitOfMeasureDTO createUnitOfMeasure(UnitOfMeasureDTO unitOfMeasureDTO);

    // Update an existing UnitOfMeasure
    public UnitOfMeasureDTO updateUnitOfMeasure(Long unitOfMeasureId, UnitOfMeasureDTO unitOfMeasureDTO);

    // Remove a UnitOfMeasure by its ID (handle cases where the UnitOfMeasure is linked to other entities)
    public void deleteUnitOfMeasure(Long unitOfMeasureId);

    // Fetch a UnitOfMeasure by its ID
    public UnitOfMeasureDTO getUnitOfMeasureById(Long unitOfMeasureId);

    // List all categories
    public Page<UnitOfMeasureDTO> getAllUnitOfMeasures(Pageable pageable);

    public UnitOfMeasure getUnitOfMeasureEntityById(Long unitOfMeasureId);
}