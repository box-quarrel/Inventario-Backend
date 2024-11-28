package com.hameed.inventario.unit.service;

import com.hameed.inventario.exception.DuplicateCodeException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.CategoryMapper;
import com.hameed.inventario.model.dto.basic.CategoryDTO;
import com.hameed.inventario.model.entity.Category;
import com.hameed.inventario.repository.CategoryRepository;
import com.hameed.inventario.service.SaleService;
import com.hameed.inventario.service.impl.CategoryServiceImpl;
import com.hameed.inventario.service.impl.SaleServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Category Added Successfully")
    public void testAddCategory_shouldReturnAddedCategoryAsCategoryDTO() {
        //  --- Arrange ---
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryCode("TT01")
                .categoryName("TestCategory 1")
                .description("New Testing Category 1")
                .build();

        Category mockMappedCategory = Category.builder()
                .categoryCode("TT01")
                .categoryName("TestCategory 1")
                .description("New Testing Category 1")
                .build();

        Category mockResultCategory = Category.builder()
                .categoryCode("TT01")
                .categoryName("TestCategory 1")
                .description("New Testing Category 1")
                .build();
        mockResultCategory.setId(1L);

        CategoryDTO expectedCategoryDTO = CategoryDTO.builder()
                .id(1L)
                .categoryCode("TT01")
                .categoryName("TestCategory 1")
                .description("New Testing Category 1")
                .build();


        // Define behavior of the mocks
        Mockito.when(categoryMapper.categoryDTOToCategory(categoryDTO)).thenReturn(mockMappedCategory);
        Mockito.when(categoryRepository.save(mockMappedCategory)).thenReturn(mockResultCategory);
        Mockito.when(categoryMapper.categoryToCategoryDTO(mockResultCategory)).thenReturn(expectedCategoryDTO);

        // --- Act ---
        CategoryDTO resultCategoryDTO = categoryService.addCategory(categoryDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultCategoryDTO, "result category DTO is null"),
                () -> assertNotNull(resultCategoryDTO.getId(), "result category DTO does not include an ID"),
                () -> assertEquals(expectedCategoryDTO, resultCategoryDTO, "Expected category DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("Category successfully not Added because category code already exists")
    public void testAddCategory_shouldThrowDuplicateCodeException() {
        //  --- Arrange ---
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryCode("TT01")
                .categoryName("TestCategory 1")
                .description("New Testing Category 1")
                .build();

        Category mockMappedCategory = Category.builder()
                .categoryCode("TT01")
                .categoryName("TestCategory 1")
                .description("New Testing Category 1")
                .build();
        mockMappedCategory.setId(null); // Explicitly set `id` to null if needed

        // Define behavior of the mocks
        Mockito.when(categoryMapper.categoryDTOToCategory(categoryDTO)).thenReturn(mockMappedCategory);
        Mockito.when(categoryRepository.findByCategoryCode(mockMappedCategory.getCategoryCode())).thenReturn(Optional.of(new Category()));

        // --- Act and Assert ---
        assertThrows(DuplicateCodeException.class, () -> categoryService.addCategory(categoryDTO),
                "Expected DuplicateCodeException to be thrown");
    }

    @Test
    @DisplayName("Category updated successfully")
    public void testUpdateCategory_shouldReturnCategoryAsCategoryDTO() {
        // --- Arrange ---
        Long categoryId = 1L;

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryCode("TTU02")
                .categoryName("TestUpdateCategory 1")
                .description("New Testing Updating Category 1")
                .build();

        Category existingCategory = Category.builder()
                .categoryCode("TT01")
                .categoryName("TestCategory 1")
                .description("New Testing Category 1")
                .build();
        existingCategory.setId(categoryId);

        Category updatedCategory = Category.builder()
                .categoryCode("TTU02")
                .categoryName("TestUpdateCategory 1")
                .description("New Testing Updating Category 1")
                .build();
        updatedCategory.setId(categoryId);

        CategoryDTO expectedCategoryDTO = CategoryDTO.builder()
                .id(categoryId)
                .categoryCode("TTU02")
                .categoryName("TestUpdateCategory 1")
                .description("New Testing Updating Category 1")
                .build();


        // Define behavior of the mocks
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        Mockito.when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.categoryToCategoryDTO(updatedCategory)).thenReturn(expectedCategoryDTO);

        // --- Act ---
        CategoryDTO resultCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultCategoryDTO, "result updated category DTO is null"),
                () -> assertNotNull(resultCategoryDTO.getId(), "result updated category DTO does not include an ID"),
                () -> assertEquals(expectedCategoryDTO, resultCategoryDTO, "Expected category DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("Category successfully not updated because id was not found")
    public void testUpdateCategory_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long categoryId = 1L;

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryCode("TTU02")
                .categoryName("TestUpdateCategory 1")
                .description("New Testing Updating Category 1")
                .build();


        // Define behavior of the mocks
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());


        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(categoryId, categoryDTO));
    }

    @Test
    @DisplayName("Category deleted successfully")
    public void testDeleteCategory_shouldCallDeleteCategoryOnce() {
        // --- Arrange ---
        Long categoryId = 1L;
        Category category = new Category();

        // Define behavior of the mocks
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // --- Act ---
        categoryService.deleteCategory(categoryId);

        // --- Assert ---
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);
    }

    @Test
    @DisplayName("Category successfully not deleted because category was not found")
    public void testDeleteCategory_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long categoryId = 1L;


        // Define behavior of the mocks
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(categoryId), "Expected ResourceNotFoundException to be thrown");
    }

    @Test
    @DisplayName("Category retrieved successfully")
    public void testGetCategory_shouldReturnCategoryAsCategoryDTO() {
        //  --- Arrange ---
        Long categoryId = 1L;

        Category mockResultCategory = Category.builder()
                .categoryCode("TT01")
                .categoryName("TestCategory 1")
                .description("New Testing Category 1")
                .build();
        mockResultCategory.setId(categoryId);

        CategoryDTO expectedCategoryDTO = CategoryDTO.builder()
                .id(categoryId)
                .categoryCode("TT01")
                .categoryName("TestCategory 1")
                .description("New Testing Category 1")
                .build();


        // Define behavior of the mocks
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockResultCategory));
        Mockito.when(categoryMapper.categoryToCategoryDTO(mockResultCategory)).thenReturn(expectedCategoryDTO);

        // --- Act ---
        CategoryDTO resultCategoryDTO = categoryService.getCategoryById(categoryId);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultCategoryDTO, "result category DTO is null"),
                () -> assertNotNull(resultCategoryDTO.getId(), "result category DTO does not include an ID"),
                () -> assertEquals(expectedCategoryDTO, resultCategoryDTO, "Expected category DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("Category successfully not retrieved because category was not found")
    public void testGetCategory_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long categoryId = 1L;


        // Define behavior of the mocks
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(categoryId), "Expected ResourceNotFoundException to be thrown");
    }
}
