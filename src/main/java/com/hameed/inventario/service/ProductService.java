package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.ProductCreateDTO;
import com.hameed.inventario.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    // Create a new product
    public void addProduct(ProductCreateDTO productCreateDTO);

    // Update an existing product
    public void updateProduct(String productId, ProductCreateDTO productCreateDTO);

    // Remove a product
    public void removeProduct(String productId);

    // Get all products with pagination
    public Page<ProductDTO> getAllProducts(Pageable pageable);

    // Get a product by ID
    public ProductDTO getProductById(String productId);
}

