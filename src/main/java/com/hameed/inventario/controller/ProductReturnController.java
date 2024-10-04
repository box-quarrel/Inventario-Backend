package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.create.ProductReturnCreateDTO;
import com.hameed.inventario.model.dto.update.ProductReturnDTO;
import com.hameed.inventario.service.ProductReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<ProductReturnDTO> getAllProductReturns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productReturnService.getAllProductReturns(pageable);
    }


    @GetMapping("/{id}")
    public ProductReturnDTO getProductReturnById(@PathVariable Long id) {
        return productReturnService.getProductReturnById(id);
    }

    @PostMapping
    public void createProductReturn(@RequestBody ProductReturnCreateDTO ProductReturnCreateDTO) {
        productReturnService.addProductReturn(ProductReturnCreateDTO);
    }

    @PutMapping
    public void updateProductReturn(@RequestBody ProductReturnDTO productReturnDTO) {
        productReturnService.updateProductReturn(productReturnDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProductReturn(@PathVariable Long id) {
        productReturnService.removeProductReturn(id);
    }


}
