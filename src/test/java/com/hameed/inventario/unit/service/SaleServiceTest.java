//package com.hameed.inventario.unit.service;
//
//
//import com.hameed.inventario.exception.ResourceNotFoundException;
//import com.hameed.inventario.mapper.POLineMapper;
//import com.hameed.inventario.mapper.SaleMapper;
//import com.hameed.inventario.model.dto.request.SaleRequestDTO;
//import com.hameed.inventario.model.dto.response.SaleResponseDTO;
//import com.hameed.inventario.model.dto.basic.CustomerDTO;
//import com.hameed.inventario.model.entity.Sale;
//import com.hameed.inventario.model.entity.Customer;
//import com.hameed.inventario.repository.SaleRepository;
//import com.hameed.inventario.service.impl.InventoryStockServiceImpl;
//import com.hameed.inventario.service.impl.ProductServiceImpl;
//import com.hameed.inventario.service.impl.SaleServiceImpl;
//import com.hameed.inventario.service.impl.CustomerServiceImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.HashSet;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class SaleServiceTest {
//    @Mock
//    private SaleRepository saleRepository;
//    @Mock
//    private CustomerServiceImpl customerService;
//    @Mock
//    private ProductServiceImpl productService;
//    @Mock
//    private InventoryStockServiceImpl inventoryStockService;
//    @Mock
//    private SaleMapper saleMapper;
//    @Mock
//    private POLineMapper poLineMapper;
//
//    @InjectMocks
//    private SaleServiceImpl saleService;
//
//
//    @Test
//    @DisplayName("Sale Order placed Successfully")
//    public void testSell_shouldReturnSalesNumber() {
//        // --- Arrange ---
//        Customer customer = new Customer();
//        SaleRequestDTO saleRequestDTO = SaleRequestDTO.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .customerId(1L)
//                .saleItemRequestDTOS(new HashSet<>())
//                .build();
//        Sale mockMappedSale = Sale.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .build();
//        Sale mockLastSale = Sale.builder()
//                .salesNumber("SN20251001-0001")
//                .build();
//        // Define Mocks behavior
//        Mockito.when(saleMapper.saleRequestDTOToSale(saleRequestDTO)).thenReturn(mockMappedSale);
//        Mockito.when(customerService.getCustomerEntityById(saleRequestDTO.getCustomerId())).thenReturn(customer);
////        Mockito.when(poLineMapper.saleItemCreateDTOSToSaleItem(any(SaleItemCreateDTOS.class))).thenReturn(any(SaleItem.class));
////        Mockito.when(productService.getProductEntityById(new SaleItem().getId())).thenReturn(new Product());
//        Mockito.when(saleRepository.findFirstByOrderByIdDesc()).thenReturn(Optional.of(mockLastSale));
//
//        // --- Act ---
//        SaleResponseDTO saleResponseDTO = saleService.sell(saleRequestDTO);
//
//        // --- Assert ---
//        assertAll(
//                () -> assertNotNull(saleResponseDTO, "result sale response DTO is null"),
//                () -> assertNotNull(saleResponseDTO.getSaleNumber(), "result sale response DTO does not include the sale number")
//        );
//    }
//
//    @Test
//    @DisplayName("Sale Order successfully retrieved")
//    public void testGetSale_shouldReturnSaleAsSaleDTO() {
//        // --- Arrange ---
//        Long saleId = 1L;
//        Sale sale = Sale.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//
//                .customer(new Customer())
//
//                .saleItems(new HashSet<>())
//                .build();
//        SaleResponseDTO expectedSaleDTO = SaleResponseDTO.builder()
//                .id(1L)
//                .discount(3000.0)
//                .totalAmount(10000.50)
//
//                .customer(new CustomerDTO())
//
//                .saleItems(new HashSet<>())
//                .build();
//        // Define mocks behavior
//        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));
//        Mockito.when(saleMapper.saleToSaleResponseDTO(sale)).thenReturn(expectedSaleDTO);
//
//        // --- Act ---
//        SaleResponseDTO resultSaleResponseDTO = saleService.getSaleById(saleId);
//
//        // --- Assert ---
//        assertAll(
//                () -> assertNotNull(resultSaleResponseDTO, "result sale DTO is null"),
//                () -> assertNotNull(resultSaleResponseDTO.getId(), "result sale DTO does not include an ID"),
//                () -> assertEquals(expectedSaleDTO, resultSaleResponseDTO, "Expected sale DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
//        );
//
//    }
//
//    @Test
//    @DisplayName("Sale Order successfully not retrieved because sale order was not found")
//    public void testGetSale_shouldThrowResourceNotFoundException() {
//        // --- Arrange ---
//        Long saleId = 1L;
//
//        // Define Mocks behavior
//        Mockito.when(saleRepository.findById(saleId)).thenReturn(Optional.empty());
//
//        // --- Act and Assert ---
//        assertThrows(ResourceNotFoundException.class, () -> saleService.getSaleById(saleId));
//    }
//
//}
