package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.request.ProductReturnRequestDTO;
import com.hameed.inventario.model.dto.response.ProductReturnResponseDTO;
import com.hameed.inventario.model.entity.ProductReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReturnService {

    // Create a new productReturn
    public ProductReturnResponseDTO addProductReturn(ProductReturnRequestDTO productReturnRequestDTO);

    // Get all productReturns with pagination
    public Page<ProductReturnResponseDTO> getAllProductReturns(Pageable pageable);

    // Get a productReturn by ID
    public ProductReturnResponseDTO getProductReturnById(Long productReturnId);

    public ProductReturn getProductReturnEntityById(Long productReturnId);
}

