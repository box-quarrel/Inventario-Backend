package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.request.SaleRequestDTO;
import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.SaleResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/sales")
public class SaleController {
    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<SaleResponseDTO>> getAllSales(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<SaleResponseDTO> saleDTOPage = saleService.getAllSales(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Sales Retrieved Successfully", saleDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SaleResponseDTO>> getSaleById(@PathVariable Long id) {
        SaleResponseDTO saleResponseDTO = saleService.getSaleById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "Sale Retrieved Successfully", saleResponseDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<SaleResponseDTO>> addSale(@RequestBody SaleRequestDTO saleRequestDTO) {
        SaleResponseDTO saleResponseDTO = saleService.sell(saleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Sale Created Successfully", saleResponseDTO));  // 201 CREATED
    }

    // sales are immutable, no update or delete
}
