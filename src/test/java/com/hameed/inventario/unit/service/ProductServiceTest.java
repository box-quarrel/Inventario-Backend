package com.hameed.inventario.unit.service;

import com.hameed.inventario.exception.DuplicateCodeException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.ProductMapper;
import com.hameed.inventario.model.dto.request.ProductRequestDTO;
import com.hameed.inventario.model.dto.basic.CategoryDTO;
import com.hameed.inventario.model.dto.response.ProductResponseDTO;
import com.hameed.inventario.model.dto.basic.UnitOfMeasureDTO;
import com.hameed.inventario.model.entity.Category;
import com.hameed.inventario.model.entity.Product;
import com.hameed.inventario.model.entity.UnitOfMeasure;
import com.hameed.inventario.repository.ProductRepository;
import com.hameed.inventario.service.impl.CategoryServiceImpl;
import com.hameed.inventario.service.impl.ProductServiceImpl;
import com.hameed.inventario.service.impl.UnitOfMeasureServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private UnitOfMeasureServiceImpl unitOfMeasureService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Product Added Successfully")
    public void testAddProduct_shouldReturnAddedProductAsProductDTO() {
        // Arrange
        Category category = new Category();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .categoryId(1L)
                .primaryUomId(1L)
                .productCode("P001")
                .productName("Product 1")
                .description("Product 1 Description")
                .currentPrice(100.0)
                .build();
        Product mockMappedProduct = Product.builder()
                .category(category)
                .primaryUom(unitOfMeasure)
                .productCode("P001")
                .productName("Product 1")
                .description("Product 1 Description")
                .currentPrice(100.0)
                .build();
        ProductResponseDTO mockProductResponseDTO = ProductResponseDTO.builder()
                .id(1L)
                .category(CategoryDTO.builder().id(1L).build())
                .primaryUom(UnitOfMeasureDTO.builder().id(1L).build())
                .productCode("P001")
                .productName("Product 1")
                .description("Product 1 Description")
                .currentPrice(100.0)
                .build();

        Mockito.when(categoryService.getCategoryEntityById(productRequestDTO.getCategoryId())).thenReturn(category);
        Mockito.when(unitOfMeasureService.getUnitOfMeasureEntityById(productRequestDTO.getPrimaryUomId())).thenReturn(unitOfMeasure);
        Mockito.when(productMapper.ProductRequestDTOToProduct(productRequestDTO)).thenReturn(mockMappedProduct);
        Mockito.when(productRepository.save(mockMappedProduct)).thenReturn(mockMappedProduct);
        Mockito.when(productMapper.productToProductResponseDTO(mockMappedProduct)).thenReturn(mockProductResponseDTO);

        // Act
        ProductResponseDTO addedProduct = productService.addProduct(productRequestDTO);

        // Assert
        assertNotNull(addedProduct);
        assertEquals(mockProductResponseDTO, addedProduct);
    }

    @Test
    @DisplayName("Product Not Found")
    public void test_shouldThrowResourceNotFoundException_whenProductNotFound() {
        // Arrange
        Long productId = 1L;
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    @DisplayName("Product Retrieved Successfully")
    public void testGetProductById_shouldReturnProductResponseDTO() {
        // Arrange
        Long productId = 1L;
        Product product = Product.builder().build();
        product.setId(productId);
        ProductResponseDTO expectedProductResponseDTO = ProductResponseDTO.builder().id(productId).build();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productMapper.productToProductResponseDTO(product)).thenReturn(expectedProductResponseDTO);

        // Act
        ProductResponseDTO result = productService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedProductResponseDTO, result);
    }

    @Test
    @DisplayName("Product Code Already Exists")
    public void testAddProduct_shouldThrowDuplicateCodeException() {
        // Arrange
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .productCode("P001")
                .build();
        Product existingProduct = Product.builder()
                .productCode("P001")
                .build();

        Mockito.when(productRepository.findByProductCode(productRequestDTO.getProductCode())).thenReturn(Optional.of(existingProduct));

        // Act and Assert
        assertThrows(DuplicateCodeException.class, () -> productService.addProduct(productRequestDTO));
    }

    @Test
    @DisplayName("Product Updated Successfully")
    public void testUpdateProduct_shouldReturnUpdatedProductAsProductDTO() {
        // Arrange
        Long productId = 1L;
        Category category = new Category();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .categoryId(1L)
                .primaryUomId(1L)
                .productCode("P001")
                .productName("Updated Product 1")
                .description("Updated Product 1 Description")
                .currentPrice(150.0)
                .build();
        Product existingProduct = Product.builder()
                .category(category)
                .primaryUom(unitOfMeasure)
                .productCode("P001")
                .productName("Product 1")
                .description("Product 1 Description")
                .currentPrice(100.0)
                .build();
        existingProduct.setId(productId);
        Product updatedProduct = Product.builder()
                .category(category)
                .primaryUom(unitOfMeasure)
                .productCode("P001")
                .productName("Updated Product 1")
                .description("Updated Product 1 Description")
                .currentPrice(150.0)
                .build();
        updatedProduct.setId(productId);
        ProductResponseDTO expectedProductResponseDTO = ProductResponseDTO.builder()
                .id(productId)
                .category(CategoryDTO.builder().id(1L).build())
                .primaryUom(UnitOfMeasureDTO.builder().id(1L).build())
                .productCode("P001")
                .productName("Updated Product 1")
                .description("Updated Product 1 Description")
                .currentPrice(150.0)
                .build();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        Mockito.when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        Mockito.when(productMapper.productToProductResponseDTO(updatedProduct)).thenReturn(expectedProductResponseDTO);

        // Act
        ProductResponseDTO result = productService.updateProduct(productId, productRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedProductResponseDTO, result);
    }

    @Test
    @DisplayName("Product Successfully Not Updated Because Product Not Found")
    public void testUpdateProduct_shouldThrowResourceNotFoundException() {
        // Arrange
        Long productId = 1L;
        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .productCode("P001")
                .build();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, productRequestDTO));
    }

    @Test
    @DisplayName("Product Deleted Successfully")
    public void testRemoveProduct_shouldCallDeleteProductOnce() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        productService.removeProduct(productId);

        // Assert
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }

    @Test
    @DisplayName("Product Successfully Not Deleted Because Product Not Found")
    public void testRemoveProduct_shouldThrowResourceNotFoundException() {
        // Arrange
        Long productId = 1L;

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.removeProduct(productId));
    }

    @Test
    @DisplayName("Get All Products Successfully")
    public void testGetAllProducts_shouldReturnPageOfProductResponseDTOs() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Product product = new Product();
        product.setId(1L);
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().id(1L).build();
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product), pageable, 1);

        Mockito.when(productRepository.findAll(pageable)).thenReturn(productPage);
        Mockito.when(productMapper.productToProductResponseDTO(product)).thenReturn(productResponseDTO);

        // Act
        Page<ProductResponseDTO> resultPage = productService.getAllProducts(pageable);

        // Assert
        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(productResponseDTO, resultPage.getContent().get(0));
    }
}