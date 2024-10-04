package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.update.SupplierDTO;
import com.hameed.inventario.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public Page<SupplierDTO> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return supplierService.getAllSuppliers(pageable);
    }


    @GetMapping("/{id}")
    public SupplierDTO getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    public void addSupplier(@RequestBody SupplierDTO supplierDTO) {
        supplierService.addSupplier(supplierDTO);
    }

    @PutMapping
    public void updateSupplier(@RequestBody SupplierDTO supplierDTO) {
        supplierService.updateSupplier(supplierDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }


}