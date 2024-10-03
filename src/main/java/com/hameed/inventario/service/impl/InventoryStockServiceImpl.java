package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.InventoryOutOfStockException;
import com.hameed.inventario.model.entity.Product;
import com.hameed.inventario.service.InventoryStockService;
import com.hameed.inventario.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryStockServiceImpl implements InventoryStockService {

    private final ProductService productService;

    @Autowired
    public InventoryStockServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void increaseStock(Long productId, int quantity) {
        Product product = productService.getProductEntityById(productId);
        product.setQuantity(product.getQuantity() + quantity);
    }

    @Override
    public void decreaseStock(Long productId, int quantity) {
        if (!checkStock(productId, quantity)) {
            throw new InventoryOutOfStockException("There is not enough stock in the inventory for product: " + productId + ", Quantity Requested: " + quantity);
        }

        Product product = productService.getProductEntityById(productId);
        product.setQuantity(product.getQuantity() - quantity);
    }

    @Override
    public boolean checkStock(Long productId, int quantity) {
        return productService.getProductEntityById(productId).getQuantity() >= quantity;
    }
}
