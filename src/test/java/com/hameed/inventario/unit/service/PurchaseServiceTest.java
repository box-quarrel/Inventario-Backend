//package com.hameed.inventario.unit.service;
//
//import com.hameed.inventario.enums.PurchaseStatus;
//import com.hameed.inventario.exception.RecordCannotBeModifiedException;
//import com.hameed.inventario.exception.ResourceNotFoundException;
//import com.hameed.inventario.mapper.POLineMapper;
//import com.hameed.inventario.mapper.PurchaseMapper;
//import com.hameed.inventario.model.dto.request.PurchaseRequestDTO;
//import com.hameed.inventario.model.dto.response.PurchaseResponseDTO;
//import com.hameed.inventario.model.dto.request.ReceiveOrderDTO;
//import com.hameed.inventario.model.dto.basic.SupplierDTO;
//import com.hameed.inventario.model.entity.PurchaseOrder;
//import com.hameed.inventario.model.entity.Supplier;
//import com.hameed.inventario.repository.PurchaseRepository;
//import com.hameed.inventario.service.impl.InventoryStockServiceImpl;
//import com.hameed.inventario.service.impl.ProductServiceImpl;
//import com.hameed.inventario.service.impl.PurchaseServiceImpl;
//import com.hameed.inventario.service.impl.SupplierServiceImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class PurchaseServiceTest {
//    @Mock
//    private PurchaseRepository purchaseRepository;
//    @Mock
//    private SupplierServiceImpl supplierService;
//    @Mock
//    private ProductServiceImpl productService;
//    @Mock
//    private InventoryStockServiceImpl inventoryStockService;
//    @Mock
//    private PurchaseMapper purchaseMapper;
//    @Mock
//    private POLineMapper poLineMapper;
//
//    @InjectMocks
//    private PurchaseServiceImpl purchaseService;
//
//
//    @Test
//    @DisplayName("Purchase Order placed Successfully")
//    public void testAddPurchaseOrder_shouldReturnPurchaseNumber() {
//        // --- Arrange ---
//        Supplier supplier = new Supplier();
//        PurchaseRequestDTO purchaseRequestDTO = PurchaseRequestDTO.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .notes("New Testing Purchase")
//                .supplierId(1L)
//                .poLineRequestDTOS(new ArrayList<>())
//                .build();
//        PurchaseOrder mockMappedPurchaseOrder = PurchaseOrder.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .notes("New Testing Purchase")
//                .build();
//        PurchaseOrder mockLastPurchaseOrder = PurchaseOrder.builder()
//                .purchaseNumber("PO20251001-0001")
//                .build();
//        // Define Mocks behavior
//        Mockito.when(purchaseMapper.PurchaseRequestDTOToPurchaseOrder(purchaseRequestDTO)).thenReturn(mockMappedPurchaseOrder);
//        Mockito.when(supplierService.getSupplierEntityById(purchaseRequestDTO.getSupplierId())).thenReturn(supplier);
////        Mockito.when(poLineMapper.poLineCreateDTOToPurchaseLine(any(POLineCreateDTO.class))).thenReturn(any(PurchaseLine.class));
////        Mockito.when(productService.getProductEntityById(new PurchaseLine().getId())).thenReturn(new Product());
//        Mockito.when(purchaseRepository.findFirstByOrderByIdDesc()).thenReturn(Optional.of(mockLastPurchaseOrder));
//
//        // --- Act ---
//        PurchaseResponseDTO purchaseResponseDTO = purchaseService.addPurchaseOrder(purchaseRequestDTO);
//
//        // --- Assert ---
//        assertAll(
//                () -> assertNotNull(purchaseResponseDTO, "result purchase response DTO is null"),
//                () -> assertNotNull(purchaseResponseDTO.getPurchaseNumber(), "result purchase response DTO does not include the purchase number")
//        );
//    }
//
//    @Test
//    @DisplayName("Purchase Order received successfully")
//    public void testReceivePurchaseOrder_shouldReturnReceivedPurchaseAsPurchaseDTO() {
//        // --- Arrange ---
//        ReceiveOrderDTO receiveOrderDTO = ReceiveOrderDTO.builder()
//                .purchaseOrderId(1L)
//                .receivedLines(new ArrayList<>())
//                .build();
//        PurchaseOrder existingPurchaseOrder = PurchaseOrder.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .purchaseStatus(PurchaseStatus.PENDING.toString())
//                .notes("New Testing Purchase")
//                .supplier(new Supplier())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        existingPurchaseOrder.setId(1L);
//        PurchaseOrder mockResultPurchaseOrder = PurchaseOrder.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .purchaseStatus(PurchaseStatus.RECEIVED.toString())
//                .notes("New Testing Purchase")
//                .supplier(new Supplier())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        existingPurchaseOrder.setId(1L);
//        PurchaseResponseDTO expectedPurchaseResponseDTO = PurchaseResponseDTO.builder()
//                .id(1L)
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .purchaseStatus(PurchaseStatus.RECEIVED.toString())
//                .notes("New Testing Purchase")
//                .supplier(new SupplierDTO())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        // Define Mocks behavior
//        Mockito.when(purchaseRepository.findById(receiveOrderDTO.getPurchaseOrderId())).thenReturn(Optional.of(existingPurchaseOrder));
//        Mockito.when(purchaseRepository.save(existingPurchaseOrder)).thenReturn(mockResultPurchaseOrder);
//        Mockito.when(purchaseMapper.purchaseOrderToPurchaseResponseDTO(mockResultPurchaseOrder)).thenReturn(expectedPurchaseResponseDTO);
//
//        // --- Act ---
//        PurchaseResponseDTO resutlPurchaseResponseDTO = purchaseService.receiveOrder(receiveOrderDTO);
//
//        // --- Assert ---
//        assertAll(
//                () -> assertNotNull(resutlPurchaseResponseDTO, "result purchase response DTO is null"),
//                () -> assertEquals(PurchaseStatus.RECEIVED.toString(), resutlPurchaseResponseDTO.getPurchaseStatus()),
//                () -> assertEquals(expectedPurchaseResponseDTO, resutlPurchaseResponseDTO)
//        );
//
//    }
//    // TODO: two more test cases should be added regarding testReceivePurchaseOrder (for throwing ResourceNotFoundException )
//
//    @Test
//    @DisplayName("Purchase Order updated successfully")
//    public void testUpdatePurchaseOrder_shouldReturnUpdatedPurchaseAsPurchaseDTO() {
//        // --- Arrange ---
//        Supplier supplier = new Supplier();
//        PurchaseResponseDTO purchaseResponseDTO = PurchaseResponseDTO.builder()
//                .id(1L)
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .notes("New Testing Purchase")
//                .supplier(new SupplierDTO())
//                .purchaseStatus(PurchaseStatus.PENDING.toString())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        PurchaseOrder existingPurchaseOrder = PurchaseOrder.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .notes("New Testing Purchase")
//                .supplier(new Supplier())
//                .purchaseStatus(PurchaseStatus.PENDING.toString())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        existingPurchaseOrder.setId(1L);
//        PurchaseOrder mockResultPurchaseOrder = PurchaseOrder.builder()
//                .discount(500.0)
//                .totalAmount(45000.0)
//                .notes("New Testing Updating Purchase")
//                .supplier(new Supplier())
//                .purchaseStatus(PurchaseStatus.PENDING.toString())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        mockResultPurchaseOrder.setId(1L);
//        PurchaseResponseDTO expectedPurchaseResponseDTO = PurchaseResponseDTO.builder()
//                .id(1L)
//                .discount(500.0)
//                .totalAmount(45000.0)
//                .notes("New Testing Updating Purchase")
//                .supplier(new SupplierDTO())
//                .purchaseStatus(PurchaseStatus.PENDING.toString())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        // Define Mocks behavior
//        Mockito.when(purchaseRepository.findById(purchaseResponseDTO.getId())).thenReturn(Optional.of(existingPurchaseOrder));
//        Mockito.when(supplierService.getSupplierEntityById(purchaseResponseDTO.getSupplier().getId())).thenReturn(supplier);
//        Mockito.when(purchaseRepository.save(existingPurchaseOrder)).thenReturn(mockResultPurchaseOrder);
//        Mockito.when(purchaseMapper.purchaseOrderToPurchaseResponseDTO(mockResultPurchaseOrder)).thenReturn(expectedPurchaseResponseDTO);
//
//        // --- Act ---
//        PurchaseResponseDTO resultPurchaseResponseDTO = purchaseService.updatePurchase(purchaseResponseDTO);
//
//        // --- Assert ---
//        assertAll(
//                () -> assertNotNull(resultPurchaseResponseDTO, "result purchase response DTO is null"),
//                () -> assertEquals(expectedPurchaseResponseDTO, resultPurchaseResponseDTO)
//        );
//    }
//
//    @Test
//    @DisplayName("Purchase Order successfully not updated because order already received")
//    public void testUpdatePurchaseOrder_shouldThrowRecordCannotBeModifiedException() {
//        // --- Arrange ---
//        PurchaseResponseDTO purchaseResponseDTO = PurchaseResponseDTO.builder()
//                .id(1L)
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .notes("New Testing Purchase")
//                .supplier(new SupplierDTO())
//                .purchaseStatus(PurchaseStatus.RECEIVED.toString())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        PurchaseOrder existingPurchaseOrder = PurchaseOrder.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .notes("New Testing Purchase")
//                .supplier(new Supplier())
//                .purchaseStatus(PurchaseStatus.RECEIVED.toString())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        existingPurchaseOrder.setId(1L);
//
//        // Define Mocks behavior
//        Mockito.when(purchaseRepository.findById(purchaseResponseDTO.getId())).thenReturn(Optional.of(existingPurchaseOrder));
//
//        // --- Act and Assert ---
//        assertThrows(RecordCannotBeModifiedException.class, () -> purchaseService.updatePurchase(purchaseResponseDTO));
//    }
//
//    @Test
//    @DisplayName("Purchase Order successfully not updated because purchase order was not found")
//    public void testUpdatePurchaseOrder_shouldThrowResourceNotFoundException() {
//        // --- Arrange ---
//        PurchaseResponseDTO purchaseResponseDTO = PurchaseResponseDTO.builder()
//                .id(1L)
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .notes("New Testing Purchase")
//                .supplier(new SupplierDTO())
//                .purchaseStatus(PurchaseStatus.PENDING.toString())
//                .purchaseLines(new ArrayList<>())
//                .build();
//
//        // Define Mocks behavior
//        Mockito.when(purchaseRepository.findById(purchaseResponseDTO.getId())).thenReturn(Optional.empty());
//
//        // --- Act and Assert ---
//        assertThrows(ResourceNotFoundException.class, () -> purchaseService.updatePurchase(purchaseResponseDTO));
//    }
//
//    @Test
//    @DisplayName("Purchase Order deleted successfully")
//    public void testDeletePurchaseOrder_shouldCallDeleteProductOnce() {
//        // --- Arrange ---
//        Long purchaseId = 1L;
//        PurchaseOrder purchaseOrder = new PurchaseOrder();
//        purchaseOrder.setId(purchaseId);
//        purchaseOrder.setPurchaseStatus(PurchaseStatus.PENDING.toString());
//
//        // Define Mocks behavior
//        Mockito.when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchaseOrder));
//
//        // --- Act ---
//        purchaseService.removePurchase(purchaseId);
//        // --- Assert ---
//        verify(purchaseRepository, Mockito.times(1)).delete(purchaseOrder);
//    }
//
//    @Test
//    @DisplayName("Purchase Order successfully not deleted because purchase order was already received")
//    public void testDeletePurchaseOrder_shouldThrowRecordCannotBeModifiedException() {
//        // --- Arrange ---
//        Long purchaseId = 1L;
//        PurchaseOrder purchaseOrder = new PurchaseOrder();
//        purchaseOrder.setId(purchaseId);
//        purchaseOrder.setPurchaseStatus(PurchaseStatus.RECEIVED.toString());
//
//        // Define Mocks behavior
//        Mockito.when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchaseOrder));
//
//        // --- Act and Assert ---
//        assertThrows(RecordCannotBeModifiedException.class, () -> purchaseService.removePurchase(purchaseId));
//    }
//
//    @Test
//    @DisplayName("Purchase Order successfully not deleted because purchase order was not found")
//    public void testDeletePurchaseOrder_shouldThrowResourceNotFoundException() {
//        // --- Arrange ---
//        Long purchaseId = 1L;
//
//        // Define Mocks behavior
//        Mockito.when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.empty());
//
//        // --- Act and Assert ---
//        assertThrows(ResourceNotFoundException.class, () -> purchaseService.removePurchase(purchaseId));
//    }
//
//    @Test
//    @DisplayName("Purchase Order successfully retrieved")
//    public void testGetPurchaseOrder_shouldReturnPurchaseOrderAsPurchaseDTO() {
//        // --- Arrange ---
//        Long purchaseId = 1L;
//        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .notes("New Testing Purchase")
//                .supplier(new Supplier())
//                .purchaseStatus(PurchaseStatus.PENDING.toString())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        PurchaseResponseDTO expectedPurchaseResponseDTO = PurchaseResponseDTO.builder()
//                .id(1L)
//                .discount(3000.0)
//                .totalAmount(10000.50)
//                .notes("New Testing Purchase")
//                .supplier(new SupplierDTO())
//                .purchaseStatus(PurchaseStatus.PENDING.toString())
//                .purchaseLines(new ArrayList<>())
//                .build();
//        // Define mocks behavior
//        Mockito.when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchaseOrder));
//        Mockito.when(purchaseMapper.purchaseOrderToPurchaseResponseDTO(purchaseOrder)).thenReturn(expectedPurchaseResponseDTO);
//
//        // --- Act ---
//        PurchaseResponseDTO resultPurchaseResponseDTO = purchaseService.getPurchaseById(purchaseId);
//
//        // --- Assert ---
//        assertAll(
//                () -> assertNotNull(resultPurchaseResponseDTO, "result purchase DTO is null"),
//                () -> assertNotNull(resultPurchaseResponseDTO.getId(), "result purchase DTO does not include an ID"),
//                () -> assertEquals(expectedPurchaseResponseDTO, resultPurchaseResponseDTO, "Expected purchase DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
//        );
//
//    }
//
//    @Test
//    @DisplayName("Purchase Order successfully not retrieved because purchase order was not found")
//    public void testGetPurchaseOrder_shouldThrowResourceNotFoundException() {
//        // --- Arrange ---
//        Long purchaseId = 1L;
//
//        // Define Mocks behavior
//        Mockito.when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.empty());
//
//        // --- Act and Assert ---
//        assertThrows(ResourceNotFoundException.class, () -> purchaseService.getPurchaseById(purchaseId));
//    }
//
//}
