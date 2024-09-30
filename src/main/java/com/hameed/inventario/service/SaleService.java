package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.SaleCreateDTO;
import com.hameed.inventario.model.dto.SaleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {

    // Create a new sale, returns salesNumber
    public String sell(SaleCreateDTO saleCreateDTO);

    // Update an existing sale, this should be authorization restricted
    public void updateSale(String saleId, SaleCreateDTO saleCreateDTO);

    // Remove a sale, this should be authorization restricted
    public void removeSale(String saleId);

    // Get all sales with pagination
    public Page<SaleDTO> getAllSales(Pageable pageable);

    // Get a sale by ID
    public SaleDTO getSaleById(String saleId);
}
