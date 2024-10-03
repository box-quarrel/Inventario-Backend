package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.ProductCreateDTO;
import com.hameed.inventario.model.dto.ProductDTO;
import com.hameed.inventario.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    // Create a new product
    public void addProduct(ProductCreateDTO productCreateDTO);

    // Update an existing product
    public void updateProduct(Long productId, ProductCreateDTO productCreateDTO);

    // Remove a product
    public void removeProduct(Long productId);

    // Get all products with pagination
    public Page<ProductDTO> getAllProducts(Pageable pageable);

    // Get a product by ID
    public ProductDTO getProductById(Long productId);

    public Product getProductEntityById(Long productId);
}

