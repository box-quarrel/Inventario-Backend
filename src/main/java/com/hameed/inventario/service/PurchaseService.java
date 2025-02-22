package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.request.PurchaseRequestDTO;
import com.hameed.inventario.model.dto.response.PurchaseResponseDTO;
import com.hameed.inventario.model.dto.request.ReceiveOrderDTO;
import com.hameed.inventario.model.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseService {

    // Create a new purchase, returns purchaseNumber
    public PurchaseResponseDTO addPurchaseOrder(PurchaseRequestDTO purchaseRequestDTO);

    // Update an existing purchase
    // TODO: this should be authorization restricted
    public PurchaseResponseDTO updatePurchaseOrder(Long purchaseId, PurchaseRequestDTO purchaseRequestDTO);

    // Remove a purchase
    // TODO: this should be authorization restricted
    public void removePurchaseOrder(Long purchaseId);

    // Get all purchases with pagination
    public Page<PurchaseResponseDTO> getAllPurchaseOrders(Pageable pageable);

    // Get a purchase by ID
    public PurchaseResponseDTO getPurchaseOrderById(Long purchaseId);

    public PurchaseOrder getPurchaseEntityById(Long purchaseId);

    public PurchaseResponseDTO receiveOrder(Long purchaseOrderId, ReceiveOrderDTO receiveOrderDTO);
}
