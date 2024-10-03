package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.CategoryDTO;
import com.hameed.inventario.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface CategoryService {
    // Create a new category
    public void createCategory(CategoryDTO categoryDTO);

    // Update an existing category
    public void updateCategory(Long categoryId, CategoryDTO categoryDTO);

    // Remove a category by its ID
    // handle cases where the category is linked to other entities (such as products)
    public void deleteCategory(Long categoryId);

    // Fetch a category by its ID
    public CategoryDTO getCategoryById(Long categoryId);

    // List all categories
    public Page<CategoryDTO> getAllCategories(Pageable pageable);

    // Gets the category entity by ID
    public Category getCategoryEntityById(Long categoryId);
}