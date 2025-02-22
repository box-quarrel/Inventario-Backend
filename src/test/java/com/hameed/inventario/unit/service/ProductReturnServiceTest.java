package com.hameed.inventario.unit.service;

import com.hameed.inventario.exception.InvalidReturnQuantityException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.ProductReturnMapper;
import com.hameed.inventario.model.dto.request.ProductReturnRequestDTO;
import com.hameed.inventario.model.dto.response.ProductReturnResponseDTO;
import com.hameed.inventario.model.entity.Product;
import com.hameed.inventario.model.entity.ProductReturn;
import com.hameed.inventario.model.entity.Sale;
import com.hameed.inventario.model.entity.SaleItem;
import com.hameed.inventario.repository.ProductReturnRepository;
import com.hameed.inventario.service.InventoryStockService;
import com.hameed.inventario.service.ProductService;
import com.hameed.inventario.service.SaleService;
import com.hameed.inventario.service.impl.ProductReturnServiceImpl;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductReturnServiceTest {

    @Mock
    private ProductReturnRepository productReturnRepository;

    @Mock
    private ProductService productService;

    @Mock
    private SaleService saleService;

    @Mock
    private InventoryStockService inventoryStockService;

    @Mock
    private ProductReturnMapper productReturnMapper;

    @InjectMocks
    private ProductReturnServiceImpl productReturnService;

    @Test
    @DisplayName("Product return added successfully")
    public void testAddProductReturn_shouldReturnProductReturnResponseDTO() {
        // Arrange
        ProductReturnRequestDTO productReturnRequestDTO = ProductReturnRequestDTO.builder()
                .productId(1L)
                .saleId(1L)
                .quantityReturned(2)
                .build();

        Product product = new Product();
        product.setId(1L);
        SaleItem saleItem = SaleItem.builder().product(product).quantity(5).build();
        Sale sale = Sale.builder().saleItems(new HashSet<>(List.of(saleItem))).build();
        sale.setId(1L);
        ProductReturn productReturn = ProductReturn.builder().product(product).quantityReturned(2).sale(sale).build();
        ProductReturn savedProductReturn = ProductReturn.builder().product(product).quantityReturned(2).sale(sale).build();
        savedProductReturn.setId(1L);
        ProductReturnResponseDTO expectedResponseDTO = ProductReturnResponseDTO.builder().id(1L).build();

        Mockito.when(productService.getProductEntityById(1L)).thenReturn(product);
        Mockito.when(saleService.getSaleEntityById(1L)).thenReturn(sale);
        Mockito.when(productReturnMapper.ProductReturnRequestDTOToProductReturn(productReturnRequestDTO)).thenReturn(productReturn);
        Mockito.when(productReturnRepository.save(productReturn)).thenReturn(savedProductReturn);
        Mockito.when(productReturnMapper.productReturnToProductReturnResponseDTO(savedProductReturn)).thenReturn(expectedResponseDTO);

        // Act
        ProductReturnResponseDTO result = productReturnService.addProductReturn(productReturnRequestDTO);

        // Assert
        assertNotNull(result, "result is null");
        assertEquals(expectedResponseDTO, result, "Expected response DTO is not correct");
    }

    @Test
    @DisplayName("Product return fails due to invalid return quantity")
    public void testAddProductReturn_shouldThrowInvalidReturnQuantityException() {
        // Arrange
        ProductReturnRequestDTO productReturnRequestDTO = ProductReturnRequestDTO.builder()
                .productId(1L)
                .saleId(1L)
                .quantityReturned(10)
                .build();

        Product product = new Product();
        product.setId(1L);
        Sale sale = Sale.builder().saleItems(Collections.singleton(
                SaleItem.builder().product(product).quantity(5).build()
        )).build();
        sale.setId(1L);

        ProductReturn productReturn = ProductReturn.builder()
                .product(product)
                .quantityReturned(10)
                .build();

        Mockito.when(productService.getProductEntityById(1L)).thenReturn(product);
        Mockito.when(saleService.getSaleEntityById(1L)).thenReturn(sale);
        Mockito.when(productReturnMapper.ProductReturnRequestDTOToProductReturn(productReturnRequestDTO)).thenReturn(productReturn);

        // Act and Assert
        assertThrows(InvalidReturnQuantityException.class, () -> productReturnService.addProductReturn(productReturnRequestDTO));
    }

    @Test
    @DisplayName("Product return retrieved successfully")
    public void testGetProductReturnById_shouldReturnProductReturnResponseDTO() {
        // Arrange
        Long productReturnId = 1L;
        ProductReturn productReturn = new ProductReturn();
        productReturn.setId(productReturnId);
        ProductReturnResponseDTO expectedResponseDTO = ProductReturnResponseDTO.builder().id(productReturnId).build();

        Mockito.when(productReturnRepository.findById(productReturnId)).thenReturn(Optional.of(productReturn));
        Mockito.when(productReturnMapper.productReturnToProductReturnResponseDTO(productReturn)).thenReturn(expectedResponseDTO);

        // Act
        ProductReturnResponseDTO result = productReturnService.getProductReturnById(productReturnId);

        // Assert
        assertNotNull(result, "result is null");
        assertEquals(expectedResponseDTO, result, "Expected response DTO is not correct");
    }

    @Test
    @DisplayName("Product return not found")
    public void testGetProductReturnById_shouldThrowResourceNotFoundException() {
        // Arrange
        Long productReturnId = 1L;

        Mockito.when(productReturnRepository.findById(productReturnId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> productReturnService.getProductReturnById(productReturnId));
    }

    @Test
    @DisplayName("Get all product returns successfully")
    public void testGetAllProductReturns_shouldReturnPageOfProductReturnResponseDTOs() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        ProductReturn productReturn = new ProductReturn();
        productReturn.setId(1L);
        ProductReturnResponseDTO productReturnResponseDTO = ProductReturnResponseDTO.builder().id(1L).build();
        Page<ProductReturn> productReturnPage = new PageImpl<>(Collections.singletonList(productReturn), pageable, 1);

        Mockito.when(productReturnRepository.findAll(pageable)).thenReturn(productReturnPage);
        Mockito.when(productReturnMapper.productReturnToProductReturnResponseDTO(productReturn)).thenReturn(productReturnResponseDTO);

        // Act
        Page<ProductReturnResponseDTO> resultPage = productReturnService.getAllProductReturns(pageable);

        // Assert
        assertNotNull(resultPage, "result page is null");
        assertEquals(1, resultPage.getTotalElements(), "Total elements in result page is not correct");
        assertEquals(productReturnResponseDTO, resultPage.getContent().get(0), "Product return response DTO in result page is not correct");
    }


}