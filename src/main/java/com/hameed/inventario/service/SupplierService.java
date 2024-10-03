package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.SupplierDTO;
import com.hameed.inventario.model.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface SupplierService {
    // Create a new supplier
    public void createSupplier(SupplierDTO supplierDTO);

    // Update an existing Supplier
    public void updateSupplier(Long supplierId, SupplierDTO supplierDTO);

    // Remove a Supplier by its ID (handle cases where the Supplier is linked to other entities)
    public void deleteSupplier(Long supplierId);

    // Fetch a Supplier by its ID
    public SupplierDTO getSupplierById(Long supplierId);

    // List all categories
    public Page<SupplierDTO> getAllSuppliers(Pageable pageable);

    public Supplier getSupplierEntityById(Long supplierId);
}
