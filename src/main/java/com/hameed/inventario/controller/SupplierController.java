package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.update.SupplierDTO;
import com.hameed.inventario.model.dto.update.SupplierDTO;
import com.hameed.inventario.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PaginatedResponseDTO<SupplierDTO>> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SupplierDTO> supplierDTOPage = supplierService.getAllSuppliers(PageRequest.of(page, size));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Suppliers Retrieved Successfully", supplierDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SupplierDTO>> getSupplierById(@PathVariable Long id) {
        SupplierDTO supplierDTO = supplierService.getSupplierById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "Supplier Retrieved Successfully", supplierDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<SupplierDTO>> addSupplier(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO resultSupplierDTO = supplierService.addSupplier(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Supplier Created Successfully", resultSupplierDTO));  // 201 CREATED
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<SupplierDTO>> updateSupplier(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO resultSupplierDTO = supplierService.updateSupplier(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Supplier Updated Successfully", resultSupplierDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<SupplierDTO>> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>(204, "Supplier Deleted Successfully"));  // 204 NO_CONTENT
    }


}