package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.create.SaleCreateDTO;
import com.hameed.inventario.model.dto.update.SaleDTO;
import com.hameed.inventario.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<SaleDTO> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return saleService.getAllSales(pageable);
    }


    @GetMapping("/{id}")
    public SaleDTO getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @PostMapping
    public String sell(@RequestBody SaleCreateDTO saleCreateDTO) {
        return saleService.sell(saleCreateDTO);
    }

    @PutMapping
    public void updateSale(@RequestBody SaleDTO saleDTO) {
        saleService.updateSale(saleDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Long id) {
        saleService.removeSale(id);
    }


}
