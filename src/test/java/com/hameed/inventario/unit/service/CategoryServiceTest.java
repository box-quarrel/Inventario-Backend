package com.hameed.inventario.unit.service;

import com.hameed.inventario.exception.DuplicateCodeException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.CategoryMapper;
import com.hameed.inventario.model.dto.update.CategoryDTO;
import com.hameed.inventario.model.entity.Category;
import com.hameed.inventario.repository.CategoryRepository;
import com.hameed.inventario.service.impl.CategoryServiceImpl;
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
        CategoryDTO categoryDTO = createCategoryDTO("TT01", "TestCategory 1", "New Testing Category 1");
        Category mockMappedCategory = createCategory("TT01", "TestCategory 1", "New Testing Category 1", null);
        Category mockResultCategory = createCategory("TT01", "TestCategory 1", "New Testing Category 1", 1L);
        CategoryDTO expectedCategoryDTO = createCategoryDTO(1L, "TT01", "TestCategory 1", "New Testing Category 1");

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
        CategoryDTO categoryDTO = createCategoryDTO("TT01", "TestCategory 1", "New Testing Category 1");
        Category mockMappedCategory = createCategory("TT01", "TestCategory 1", "New Testing Category 1", null);

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
        CategoryDTO categoryDTO = createCategoryDTO(1L, "TTU02", "TestUpdateCategory 1", "New Testing Updating Category 1");
        Category existingCategory = createCategory("TT01", "TestCategory 1", "New Testing Category 1", 1L);
        Category updatedCategory = createCategory("TTU02", "TestUpdateCategory 1", "New Testing Updating Category 1", 1L);
        CategoryDTO expectedCategoryDTO = createCategoryDTO(1L, "TTU02", "TestUpdateCategory 1", "New Testing Updating Category 1");

        // Define behavior of the mocks
        Mockito.when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.of(existingCategory));
        Mockito.when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.categoryToCategoryDTO(updatedCategory)).thenReturn(expectedCategoryDTO);

        // --- Act ---
        CategoryDTO resultCategoryDTO = categoryService.updateCategory(categoryDTO);

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
        CategoryDTO categoryDTO = createCategoryDTO(1L, "TTU02", "TestUpdateCategory 1", "New Testing Updating Category 1");

        // Define behavior of the mocks
        Mockito.when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.empty());


        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(categoryDTO));
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
        Category mockResultCategory = createCategory("TT01", "TestCategory 1", "New Testing Category 1", 1L);
        CategoryDTO expectedCategoryDTO = createCategoryDTO(1L, "TT01", "TestCategory 1", "New Testing Category 1");

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



    // some utility builder methods
    private CategoryDTO createCategoryDTO(String code, String name, String description) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryCode(code);
        dto.setCategoryName(name);
        dto.setDescription(description);
        return dto;
    }

    private Category createCategory(String code, String name, String description, Long id) {
        Category category = new Category();
        category.setCategoryCode(code);
        category.setCategoryName(name);
        category.setDescription(description);
        if (id != null) {
            category.setId(id);
        }
        return category;
    }

    private CategoryDTO createCategoryDTO(Long id, String code, String name, String description) {
        CategoryDTO dto = createCategoryDTO(code, name, description);
        dto.setId(id);
        return dto;
    }
}
