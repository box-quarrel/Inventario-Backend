package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.request.SaleRequestDTO;
import com.hameed.inventario.model.dto.response.SaleResponseDTO;
import com.hameed.inventario.model.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {

    // Create a new sale, returns salesNumber
    public SaleResponseDTO sell(SaleRequestDTO saleRequestDTO);

    // Get all sales with pagination
    public Page<SaleResponseDTO> getAllSales(Pageable pageable);

    // Get a sale by ID
    public SaleResponseDTO getSaleById(Long saleId);

    public Sale getSaleEntityById(Long saleId);

    // sales are immutable, you cannot delete or update already created sales
}
