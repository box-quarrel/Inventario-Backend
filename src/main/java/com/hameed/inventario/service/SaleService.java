package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.create.SaleCreateDTO;
import com.hameed.inventario.model.dto.response.SaleResponseDTO;
import com.hameed.inventario.model.dto.update.SaleDTO;
import com.hameed.inventario.model.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {

    // Create a new sale, returns salesNumber
    public SaleResponseDTO sell(SaleCreateDTO saleCreateDTO);

    // Get all sales with pagination
    public Page<SaleDTO> getAllSales(Pageable pageable);

    // Get a sale by ID
    public SaleDTO getSaleById(Long saleId);

    public Sale getSaleEntityById(Long saleId);

    // sales are immutable, you cannot delete or update already created sales
}
