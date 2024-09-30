package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.SupplierDTO;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface SupplierService {
    // Create a new supplier
    public void createSupplier(SupplierDTO SupplierDTO);

    // Update an existing Supplier
    public void updateSupplier(String SupplierId, SupplierDTO SupplierDTO);

    // Remove a Supplier by its ID (handle cases where the Supplier is linked to other entities)
    public void deleteSupplier(String SupplierId);

    // Fetch a Supplier by its ID
    public SupplierDTO getSupplierById(String SupplierId);

    // List all categories
    public Page<SupplierDTO> getAllSuppliers();
}
