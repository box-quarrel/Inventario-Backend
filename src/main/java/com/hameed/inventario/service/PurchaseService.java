package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.PurchaseCreateDTO;
import com.hameed.inventario.model.dto.PurchaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseService {

    // Create a new purchase, returns purchaseNumber
    public String addPurchaseOrder(PurchaseCreateDTO purchaseCreateDTO);

    // Update an existing purchase, this should be authorization restricted
    public void updatePurchase(String purchaseId, PurchaseCreateDTO purchaseCreateDTO);

    // Remove a purchase, this should be authorization restricted
    public void removePurchase(String purchaseId);

    // Get all purchases with pagination
    public Page<PurchaseDTO> getAllPurchases(Pageable pageable);

    // Get a purchase by ID
    public PurchaseDTO getPurchaseById(String purchaseId);
}
