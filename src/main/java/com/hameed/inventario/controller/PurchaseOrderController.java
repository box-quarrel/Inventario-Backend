package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.create.PurchaseCreateDTO;
import com.hameed.inventario.model.dto.update.PurchaseDTO;
import com.hameed.inventario.model.dto.update.ReceiveOrderDTO;
import com.hameed.inventario.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseOrderController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public Page<PurchaseDTO> getAllPurchases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return purchaseService.getAllPurchases(pageable);
    }


    @GetMapping("/{id}")
    public PurchaseDTO getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @PostMapping
    public String createPurchaseOrder(@RequestBody PurchaseCreateDTO purchaseCreateDTO) {
        return purchaseService.addPurchaseOrder(purchaseCreateDTO);
    }

    @PutMapping("/receive")
    public void receiveOrder(@RequestBody ReceiveOrderDTO receiveOrderDTO) {
        purchaseService.receiveOrder(receiveOrderDTO);
    }

    @PutMapping
    public void updatePurchase(@RequestBody PurchaseDTO purchaseDTO) {
        purchaseService.updatePurchase(purchaseDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id) {
        purchaseService.removePurchase(id);
    }


}
