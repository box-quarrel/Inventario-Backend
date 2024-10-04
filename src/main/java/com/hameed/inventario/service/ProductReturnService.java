package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.create.ProductReturnCreateDTO;
import com.hameed.inventario.model.dto.update.ProductReturnDTO;
import com.hameed.inventario.model.entity.ProductReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReturnService {

    // Create a new productReturn
    public void addProductReturn(ProductReturnCreateDTO productReturnCreateDTO);

    // Update an existing productReturn
    //
    public void updateProductReturn (ProductReturnDTO productReturnDTO);

    // Remove a productReturn
    public void removeProductReturn(Long productReturnId);

    // Get all productReturns with pagination
    public Page<ProductReturnDTO> getAllProductReturns(Pageable pageable);

    // Get a productReturn by ID
    public ProductReturnDTO getProductReturnById(Long productReturnId);

    public ProductReturn getProductReturnEntityById(Long productReturnId);
}

