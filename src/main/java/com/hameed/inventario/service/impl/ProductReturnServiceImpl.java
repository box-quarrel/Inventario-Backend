package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.InvalidReturnQuantityException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.ProductReturnMapper;
import com.hameed.inventario.model.dto.request.ProductReturnRequestDTO;
import com.hameed.inventario.model.dto.response.ProductReturnResponseDTO;
import com.hameed.inventario.model.entity.Product;
import com.hameed.inventario.model.entity.ProductReturn;
import com.hameed.inventario.model.entity.Sale;
import com.hameed.inventario.model.entity.SaleItem;
import com.hameed.inventario.repository.ProductReturnRepository;
import com.hameed.inventario.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductReturnServiceImpl implements ProductReturnService {

    private final ProductReturnRepository productReturnRepository;
    private final ProductService productService;
    private final SaleService saleService;
    private final InventoryStockService inventoryStockService;
    private final ProductReturnMapper productReturnMapper;

    @Autowired
    public ProductReturnServiceImpl(ProductReturnRepository productReturnRepository,
                                    ProductService productService,
                                    SaleService saleService,
                                    InventoryStockService inventoryStockService,
                                    ProductReturnMapper productReturnMapper) {
        this.productReturnRepository = productReturnRepository;
        this.productService = productService;
        this.saleService = saleService;
        this.inventoryStockService = inventoryStockService;
        this.productReturnMapper = productReturnMapper;
    }

    @Override
    @Transactional
    public ProductReturnResponseDTO addProductReturn(ProductReturnRequestDTO productReturnRequestDTO) {
        ProductReturn productReturn = productReturnMapper.ProductReturnRequestDTOToProductReturn(productReturnRequestDTO);

        // Retrieve product and sale information
        Product product = productService.getProductEntityById(productReturnRequestDTO.getProductId());
        Sale sale = saleService.getSaleEntityById(productReturnRequestDTO.getSaleId());

        productReturn.setProduct(product);

        // Validate the return: Product exists on the sale and the return quantity is valid
        SaleItem saleItem = findSaleItemForProduct(sale, product);
        validateReturnQuantity(saleItem, productReturn); // throws relative exceptions when any of the validations fail

        // Update the sale item quantity
        saleItem.setQuantity(saleItem.getQuantity() - productReturn.getQuantityReturned());

        // Persist the product return
        productReturn.setSale(sale);
        ProductReturn savedProductReturn = productReturnRepository.save(productReturn);

        // Replenish the stock
        inventoryStockService.increaseStock(savedProductReturn.getProduct().getId(), savedProductReturn.getQuantityReturned());

        return productReturnMapper.productReturnToProductReturnResponseDTO(savedProductReturn);
    }

    // Helper method to find the sale item associated with the returned product
    private SaleItem findSaleItemForProduct(Sale sale, Product product) {
        return sale.getSaleItems().stream()
                .filter(saleItem -> saleItem.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: " + product.getId() + " was not found in the sale."));
    }

    // Helper method to validate if the return quantity is valid
    private void validateReturnQuantity(SaleItem saleItem, ProductReturn productReturn) {
        if (saleItem == null) {
            throw new ResourceNotFoundException("The product: " + productReturn.getProduct().getId() + " was not found on any of the sale items.");
        }

        if (productReturn.getQuantityReturned() > saleItem.getQuantity()) {
            throw new InvalidReturnQuantityException("Requested return quantity: " + productReturn.getQuantityReturned() + " exceeds the original sold quantity on the sale line: " + saleItem.getQuantity());
        }
    }

    @Override
    public ProductReturnResponseDTO getProductReturnById(Long productReturnId) {
        ProductReturn productReturn = getProductReturnEntityById(productReturnId);
        return productReturnMapper.productReturnToProductReturnResponseDTO(productReturn);
    }

    @Override
    public Page<ProductReturnResponseDTO> getAllProductReturns(Pageable pageable) {
        Page<ProductReturn> pageProductReturns = productReturnRepository.findAll(pageable);
        return pageProductReturns.map(productReturnMapper::productReturnToProductReturnResponseDTO);
    }

    @Override
    public ProductReturn getProductReturnEntityById(Long productReturnId) {
        return productReturnRepository.findById(productReturnId).orElseThrow(() -> new ResourceNotFoundException("ProductReturn with this Id: " + productReturnId + " could not be found"));
    }

}
