package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.create.SaleCreateDTO;
import com.hameed.inventario.model.dto.create.SaleCreateDTO;
import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.SaleResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.update.SaleDTO;
import com.hameed.inventario.model.dto.update.SaleDTO;
import com.hameed.inventario.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<SaleDTO>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SaleDTO> saleDTOPage = saleService.getAllSales(PageRequest.of(page, size));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Sales Retrieved Successfully", saleDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SaleDTO>> getSaleById(@PathVariable Long id) {
        SaleDTO saleDTO = saleService.getSaleById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "Sale Retrieved Successfully", saleDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<SaleResponseDTO>> addSale(@RequestBody SaleCreateDTO saleCreateDTO) {
        SaleResponseDTO saleResponseDTO = saleService.sell(saleCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Sale Created Successfully", saleResponseDTO));  // 201 CREATED
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<SaleDTO>> updateSale(@RequestBody SaleDTO saleDTO) {
        SaleDTO resultSaleDTO = saleService.updateSale(saleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Sale Updated Successfully", resultSaleDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<SaleDTO>> deleteSale(@PathVariable Long id) {
        saleService.removeSale(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>(204, "Sale Deleted Successfully"));  // 204 NO_CONTENT
    }
}
