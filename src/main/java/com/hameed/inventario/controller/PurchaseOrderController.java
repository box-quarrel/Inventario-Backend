package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.request.PurchaseRequestDTO;
import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.PurchaseResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.request.ReceiveOrderDTO;
import com.hameed.inventario.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/purchase-orders")
public class PurchaseOrderController {

    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseOrderController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<PurchaseResponseDTO>> getAllPurchases(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<PurchaseResponseDTO> purchaseDTOPage = purchaseService.getAllPurchaseOrders(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Purchase Orders Retrieved Successfully", purchaseDTOPage)); // 200 OK
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> getPurchaseById(@PathVariable Long id) {
        PurchaseResponseDTO purchaseResponseDTO = purchaseService.getPurchaseOrderById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "Purchase Order Retrieved Successfully", purchaseResponseDTO)); // 200 OK
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> addPurchase(@Valid @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        // remove ids from the po line request DTOs if mistakenly added
        purchaseRequestDTO.getPoLineRequestDTOS().forEach(poLineRequestDTO -> {
            if (poLineRequestDTO.getId() != null)
                poLineRequestDTO.setId(null);
        });
        PurchaseResponseDTO purchaseResponseDTO = purchaseService.addPurchaseOrder(purchaseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Purchase Order Created Successfully", purchaseResponseDTO));  // 201 CREATED
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/receive/{id}")
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> receiveOrder (@PathVariable Long id, @Valid @RequestBody ReceiveOrderDTO receiveOrderDTO){
        PurchaseResponseDTO receivedOrderDTO = purchaseService.receiveOrder(id, receiveOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Purchase Order Received Successfully", receivedOrderDTO));  // 201 CREATED
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> updatePurchase(@PathVariable Long id, @Valid @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        PurchaseResponseDTO resultPurchaseResponseDTO = purchaseService.updatePurchaseOrder(id, purchaseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Purchase Updated Successfully", resultPurchaseResponseDTO));  // 201 CREATED
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> deletePurchase(@PathVariable Long id) {
        purchaseService.removePurchaseOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
