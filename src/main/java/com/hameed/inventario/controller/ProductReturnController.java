package com.hameed.inventario.controller;


import com.hameed.inventario.model.dto.request.ProductReturnRequestDTO;
import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ProductReturnResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.service.ProductReturnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/product-returns")
public class ProductReturnController {

    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final ProductReturnService productReturnService;

    @Autowired
    public ProductReturnController(ProductReturnService productReturnService) {
        this.productReturnService = productReturnService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<ProductReturnResponseDTO>> getAllProductReturns(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<ProductReturnResponseDTO> productReturnDTOPage = productReturnService.getAllProductReturns(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "ProductReturns Retrieved Successfully", productReturnDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductReturnResponseDTO>> getProductReturnById(@PathVariable Long id) {
        ProductReturnResponseDTO productReturnResponseDTO = productReturnService.getProductReturnById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "ProductReturn Retrieved Successfully", productReturnResponseDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<ProductReturnResponseDTO>> addProductReturn(@Valid @RequestBody ProductReturnRequestDTO productReturnRequestDTO) {
        ProductReturnResponseDTO resultProductReturnResponseDTO = productReturnService.addProductReturn(productReturnRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "ProductReturn Created Successfully", resultProductReturnResponseDTO));  // 201 CREATED
    }

    // product returns are immutable, no update or delete
}
