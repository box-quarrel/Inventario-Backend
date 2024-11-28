package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.basic.SupplierDTO;
import com.hameed.inventario.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/suppliers")
public class SupplierController {
    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<SupplierDTO>> getAllSuppliers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<SupplierDTO> supplierDTOPage = supplierService.getAllSuppliers(PageRequest.of(page, finalPageSize));
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

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SupplierDTO>> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO resultSupplierDTO = supplierService.updateSupplier(id, supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Supplier Updated Successfully", resultSupplierDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<SupplierDTO>> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>(204, "Supplier Deleted Successfully"));  // 204 NO_CONTENT
    }


}