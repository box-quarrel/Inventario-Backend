package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.create.PurchaseCreateDTO;
import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.PurchaseResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.update.PurchaseDTO;
import com.hameed.inventario.model.dto.update.ReceiveOrderDTO;
import com.hameed.inventario.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/purchase-orders")
public class PurchaseOrderController {

    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseOrderController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<PurchaseDTO>> getAllPurchases(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<PurchaseDTO> purchaseDTOPage = purchaseService.getAllPurchases(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Purchase Orders Retrieved Successfully", purchaseDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PurchaseDTO>> getPurchaseById(@PathVariable Long id) {
        PurchaseDTO purchaseDTO = purchaseService.getPurchaseById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "Purchase Order Retrieved Successfully", purchaseDTO)); // 200 OK
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> addPurchase(@RequestBody PurchaseCreateDTO purchaseCreateDTO) {
        PurchaseResponseDTO purchaseResponseDTO = purchaseService.addPurchaseOrder(purchaseCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Purchase Order Created Successfully", purchaseResponseDTO));  // 201 CREATED
    }

    @PutMapping("/receive")
    public ResponseEntity<ResponseDTO<PurchaseDTO>> receiveOrder (@RequestBody ReceiveOrderDTO receiveOrderDTO){
        PurchaseDTO receivedOrderDTO = purchaseService.receiveOrder(receiveOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Purchase Order Received Successfully", receivedOrderDTO));  // 201 CREATED
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<PurchaseDTO>> updatePurchase(@RequestBody PurchaseDTO purchaseDTO) {
        PurchaseDTO resultPurchaseDTO = purchaseService.updatePurchase(purchaseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Purchase Updated Successfully", resultPurchaseDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<PurchaseDTO>> deletePurchase(@PathVariable Long id) {
        purchaseService.removePurchase(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>(204, "Purchase Deleted Successfully"));  // 204 NO_CONTENT

    }
}
