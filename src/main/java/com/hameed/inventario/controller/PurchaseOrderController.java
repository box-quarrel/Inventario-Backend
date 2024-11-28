package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.request.PurchaseRequestDTO;
import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.PurchaseResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.request.ReceiveOrderDTO;
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
    public ResponseEntity<PaginatedResponseDTO<PurchaseResponseDTO>> getAllPurchases(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<PurchaseResponseDTO> purchaseDTOPage = purchaseService.getAllPurchases(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Purchase Orders Retrieved Successfully", purchaseDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> getPurchaseById(@PathVariable Long id) {
        PurchaseResponseDTO purchaseResponseDTO = purchaseService.getPurchaseById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "Purchase Order Retrieved Successfully", purchaseResponseDTO)); // 200 OK
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> addPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        PurchaseResponseDTO purchaseResponseDTO = purchaseService.addPurchaseOrder(purchaseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Purchase Order Created Successfully", purchaseResponseDTO));  // 201 CREATED
    }

    @PutMapping("/receive")
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> receiveOrder (@RequestBody ReceiveOrderDTO receiveOrderDTO){
        PurchaseResponseDTO receivedOrderDTO = purchaseService.receiveOrder(receiveOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Purchase Order Received Successfully", receivedOrderDTO));  // 201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> updatePurchase(@PathVariable Long id, @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        PurchaseResponseDTO resultPurchaseResponseDTO = purchaseService.updatePurchase(id, purchaseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Purchase Updated Successfully", resultPurchaseResponseDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<PurchaseResponseDTO>> deletePurchase(@PathVariable Long id) {
        purchaseService.removePurchase(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>(204, "Purchase Deleted Successfully"));  // 204 NO_CONTENT

    }
}
