package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.CategoryMapper;
import com.hameed.inventario.model.dto.update.CategoryDTO;
import com.hameed.inventario.model.entity.Category;
import com.hameed.inventario.repository.CategoryRepository;
import com.hameed.inventario.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        // map to Category
        CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        // save
        categoryRepository.save(category);
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Long categoryId = categoryDTO.getId();
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            // map fields from dto to category
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setCategoryCode(categoryDTO.getCategoryCode());
            category.setDescription(categoryDTO.getDescription());

            // save
            categoryRepository.save(category);

            // return the updated DTO
            return CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
        } else {
            throw new ResourceNotFoundException("Category with this Id: " + categoryId + " could not be found");
        }
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(
                category -> {
                    // handling the link with other entities before deleting
                    category.getProducts().forEach(product -> product.setCategory(null));

                    categoryRepository.delete(category);
                },
                () -> {
                    throw new ResourceNotFoundException("Category with this Id: " + categoryId + " could not be found");
                }
        );

    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = getCategoryEntityById(categoryId);

        // get the mapper and map to category dto
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
    }

    @Override
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        // get all categories from by repo
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        // map all categories to DTOs
        return categoriesPage.map(CategoryMapper.INSTANCE::categoryToCategoryDTO);
    }

    @Override
    public Category getCategoryEntityById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with this Id: " + categoryId + " could not be found"));
    }
}
