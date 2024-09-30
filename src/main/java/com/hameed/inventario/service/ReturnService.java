package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.ProductReturnDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReturnService {

    // Create a new return
    public void addReturn(ProductReturnDTO productReturnDTO);

    // Update an existing return
    public void updateReturn(String returnId, ProductReturnDTO productReturnDTO);

    // Remove a return
    public void removeReturn(String returnId);

    // Get all returns with pagination
    public Page<ProductReturnDTO> getAllReturns(Pageable pageable);

    // Get a return by ID
    public ProductReturnDTO getReturnById(String returnId);
}

