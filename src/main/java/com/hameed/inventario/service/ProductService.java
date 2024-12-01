package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.request.ProductRequestDTO;
import com.hameed.inventario.model.dto.response.ProductResponseDTO;
import com.hameed.inventario.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    // Create a new product
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);

    // Update an existing product
    public ProductResponseDTO updateProduct (Long productId, ProductRequestDTO productRequestDTO);

    // Remove a product
    public void removeProduct(Long productId);

    // Get all products with pagination
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable);

    // Get a product by ID
    public ProductResponseDTO getProductById(Long productId);

    // for retrieving the entity itself
    public Product getProductEntityById(Long productId);

    // for direct persistence
    public void saveProduct(Product product);
}

