package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.basic.CategoryDTO;
import com.hameed.inventario.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/categories")
public class CategoryController {

    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<CategoryDTO>> getAllCategories(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<CategoryDTO> categoryDTOPage = categoryService.getAllCategories(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Categories Retrieved Successfully", categoryDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> getCategoryById(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "Category Retrieved Successfully", categoryDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<CategoryDTO>> addCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO resultCategoryDTO = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Category Created Successfully", resultCategoryDTO));  // 201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> updateCategory(@PathVariable Long id,
                                                                   @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO resultCategoryDTO = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Category Updated Successfully", resultCategoryDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>(204, "Category Deleted Successfully"));  // 204 NO_CONTENT
    }


}
