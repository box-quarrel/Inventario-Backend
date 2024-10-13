package com.hameed.inventario.controller;


import com.hameed.inventario.model.dto.create.ProductReturnCreateDTO;
import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.update.ProductReturnDTO;
import com.hameed.inventario.service.ProductReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/product-returns")
public class ProductReturnController {

    private final ProductReturnService productReturnService;

    @Autowired
    public ProductReturnController(ProductReturnService productReturnService) {
        this.productReturnService = productReturnService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<ProductReturnDTO>> getAllProductReturns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProductReturnDTO> productReturnDTOPage = productReturnService.getAllProductReturns(PageRequest.of(page, size));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "ProductReturns Retrieved Successfully", productReturnDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductReturnDTO>> getProductReturnById(@PathVariable Long id) {
        ProductReturnDTO productReturnDTO = productReturnService.getProductReturnById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "ProductReturn Retrieved Successfully", productReturnDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<ProductReturnDTO>> addProductReturn(@RequestBody ProductReturnCreateDTO productReturnCreateDTO) {
        ProductReturnDTO resultProductReturnDTO = productReturnService.addProductReturn(productReturnCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "ProductReturn Created Successfully", resultProductReturnDTO));  // 201 CREATED
    }

    // product returns are immutable, no update or delete
}
