package com.hameed.inventario.unit.service;

import com.hameed.inventario.enums.DiscountType;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.SaleItemMapper;
import com.hameed.inventario.mapper.SaleMapper;
import com.hameed.inventario.model.dto.request.SaleItemRequestDTO;
import com.hameed.inventario.model.dto.request.SaleRequestDTO;
import com.hameed.inventario.model.dto.response.SaleItemResponseDTO;
import com.hameed.inventario.model.dto.response.SaleResponseDTO;
import com.hameed.inventario.model.entity.Customer;
import com.hameed.inventario.model.entity.Product;
import com.hameed.inventario.model.entity.Sale;
import com.hameed.inventario.model.entity.SaleItem;
import com.hameed.inventario.repository.SaleRepository;
import com.hameed.inventario.service.CustomerService;
import com.hameed.inventario.service.InventoryStockService;
import com.hameed.inventario.service.ProductService;
import com.hameed.inventario.service.impl.SaleServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductService productService;

    @Mock
    private CustomerService customerService;

    @Mock
    private InventoryStockService inventoryStockService;

    @Mock
    private SaleMapper saleMapper;

    @Mock
    private SaleItemMapper saleItemMapper;

    @InjectMocks
    private SaleServiceImpl saleService;



    @Test
    @DisplayName("Sale placed successfully")
    void testSell() {
        // Arrange
        SaleItemRequestDTO saleItemRequestDTO = SaleItemRequestDTO.builder()
                .productId(1L)
                .quantity(2)
                .build();

        SaleRequestDTO saleRequestDTO = SaleRequestDTO.builder()
                .discountType(DiscountType.PERCENTAGE.name())
                .discountValue(10.0)
                .customerId(1L)
                .saleItemRequestDTOS(new HashSet<>(Collections.singletonList(saleItemRequestDTO)))
                .build();

        Customer customer = new Customer();
        customer.setId(1L);

        Product product = new Product();
        product.setId(1L);

        SaleItem saleItem = SaleItem.builder()
                .product(product)
                .quantity(2)
                .build();
        Sale sale = Sale.builder()
                .customer(customer)
                .saleItems(new HashSet<>(Collections.singletonList(saleItem)))
                .build();

        SaleResponseDTO saleResponseDTO = SaleResponseDTO.builder()
                .id(1L)
                .build();

        // Arrange
        when(saleMapper.saleRequestDTOToSale(saleRequestDTO)).thenReturn(sale);
        when(customerService.getCustomerEntityById(1L)).thenReturn(customer);
        when(saleItemMapper.SaleItemRequestDTOToSaleItem(saleItemRequestDTO)).thenReturn(saleItem);
        when(productService.getProductEntityById(1L)).thenReturn(product);
        when(saleRepository.save(sale)).thenReturn(sale);
        when(saleMapper.saleToSaleResponseDTO(sale)).thenReturn(saleResponseDTO);

        // Act
        SaleResponseDTO response = saleService.sell(saleRequestDTO);

        // Assert
        assertNotNull(response);
        verify(inventoryStockService, times(1)).decreaseStock(1L, 2);
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    void testGetAllSales() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Sale sale = new Sale();
        Page<Sale> page = new PageImpl<>(Collections.singletonList(sale));
        when(saleRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<SaleResponseDTO> result = saleService.getAllSales(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetSaleById() {
        // Arrange
        Sale sale = new Sale();
        sale.setId(1L);
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        when(saleMapper.saleToSaleResponseDTO(sale)).thenReturn(SaleResponseDTO.builder().id(1L).build());

        // Act
        SaleResponseDTO result = saleService.getSaleById(1L);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetSaleEntityById_NotFound() {
        // Arrange
        when(saleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> saleService.getSaleEntityById(1L));
    }
}