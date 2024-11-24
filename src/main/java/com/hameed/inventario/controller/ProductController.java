package com.hameed.inventario.controller;


import com.hameed.inventario.model.dto.create.ProductCreateDTO;
import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.update.ProductDTO;
import com.hameed.inventario.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/products")
public class ProductController {

    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<ProductDTO>> getAllCategories(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<ProductDTO> productDTOPage = productService.getAllProducts(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Products Retrieved Successfully", productDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "Product Retrieved Successfully", productDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<ProductDTO>> addProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        ProductDTO resultProductDTO = productService.addProduct(productCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Product Created Successfully", resultProductDTO));  // 201 CREATED
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<ProductDTO>> updateProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO resultProductDTO = productService.updateProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Product Updated Successfully", resultProductDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductDTO>> deleteProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>(204, "Product Deleted Successfully"));  // 204 NO_CONTENT
    }
    
}
