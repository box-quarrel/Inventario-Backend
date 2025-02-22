package com.hameed.inventario.unit.service;

import com.hameed.inventario.enums.PurchaseStatus;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.PurchaseMapper;
import com.hameed.inventario.model.dto.request.PurchaseRequestDTO;
import com.hameed.inventario.model.dto.response.PurchaseResponseDTO;
import com.hameed.inventario.model.entity.PurchaseOrder;
import com.hameed.inventario.repository.PurchaseRepository;
import com.hameed.inventario.service.SupplierService;
import com.hameed.inventario.service.impl.PurchaseServiceImpl;
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
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private PurchaseMapper purchaseMapper;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Test
    @DisplayName("Purchase Order Added Successfully")
    public void testAddPurchaseOrder_shouldReturnAddedPurchaseOrderAsPurchaseResponseDTO() {
        // Arrange
        PurchaseRequestDTO purchaseRequestDTO = PurchaseRequestDTO.builder()
                .notes("Test notes")
                .poLineRequestDTOS(Collections.emptyList())
                .build();
        PurchaseOrder mockMappedPurchaseOrder = new PurchaseOrder();
        mockMappedPurchaseOrder.setNotes("Test notes");
        PurchaseResponseDTO mockPurchaseResponseDTO = PurchaseResponseDTO.builder()
                .id(1L)
                .purchaseLines(Collections.emptyList())
                .notes("Test notes")
                .build();

        Mockito.when(purchaseMapper.PurchaseRequestDTOToPurchaseOrder(purchaseRequestDTO)).thenReturn(mockMappedPurchaseOrder);
        Mockito.when(purchaseRepository.save(mockMappedPurchaseOrder)).thenReturn(mockMappedPurchaseOrder);
        Mockito.when(purchaseMapper.purchaseOrderToPurchaseResponseDTO(mockMappedPurchaseOrder)).thenReturn(mockPurchaseResponseDTO);

        // Act
        PurchaseResponseDTO addedPurchaseOrder = purchaseService.addPurchaseOrder(purchaseRequestDTO);

        // Assert
        assertNotNull(addedPurchaseOrder);
        assertEquals(mockPurchaseResponseDTO, addedPurchaseOrder);
    }

    @Test
    @DisplayName("Purchase Order Not Found")
    public void test_shouldThrowResourceNotFoundException_whenPurchaseOrderNotFound() {
        // Arrange
        Long purchaseOrderId = 1L;
        Mockito.when(purchaseRepository.findById(purchaseOrderId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> purchaseService.getPurchaseOrderById(purchaseOrderId));
    }

    @Test
    @DisplayName("Purchase Order Retrieved Successfully")
    public void testGetPurchaseOrderById_shouldReturnPurchaseResponseDTO() {
        // Arrange
        Long purchaseOrderId = 1L;
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(purchaseOrderId);
        PurchaseResponseDTO expectedPurchaseResponseDTO = PurchaseResponseDTO.builder().id(purchaseOrderId).build();

        Mockito.when(purchaseRepository.findById(purchaseOrderId)).thenReturn(Optional.of(purchaseOrder));
        Mockito.when(purchaseMapper.purchaseOrderToPurchaseResponseDTO(purchaseOrder)).thenReturn(expectedPurchaseResponseDTO);

        // Act
        PurchaseResponseDTO result = purchaseService.getPurchaseOrderById(purchaseOrderId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPurchaseResponseDTO, result);
    }

    @Test
    @DisplayName("Purchase Order Updated Successfully")
    public void testUpdatePurchaseOrder_shouldReturnUpdatedPurchaseOrderAsPurchaseOrderResponseDTO() {
        // Arrange
        Long purchaseOrderId = 1L;
        PurchaseRequestDTO purchaseRequestDTO = PurchaseRequestDTO.builder()
                .notes("Updated notes")
                .poLineRequestDTOS(Collections.emptyList())
                .build();
        PurchaseOrder existingPurchaseOrder = PurchaseOrder.builder()
                .notes("Old notes")
                .purchaseLines(Collections.emptyList())
                .purchaseStatus(PurchaseStatus.PENDING.toString())
                .build();
        existingPurchaseOrder.setId(purchaseOrderId);
        PurchaseOrder updatedPurchaseOrder = PurchaseOrder.builder()
                .notes("Old notes")
                .purchaseLines(Collections.emptyList())
                .purchaseStatus(PurchaseStatus.PENDING.toString())
                .build();
        updatedPurchaseOrder.setId(purchaseOrderId);
        PurchaseResponseDTO expectedPurchaseResponseDTO = PurchaseResponseDTO.builder()
                .id(purchaseOrderId)
                .notes("Updated notes")
                .purchaseLines(Collections.emptyList())
                .purchaseStatus(PurchaseStatus.PENDING.toString())
                .build();

        Mockito.when(purchaseRepository.findById(purchaseOrderId)).thenReturn(Optional.of(existingPurchaseOrder));
        Mockito.when(purchaseRepository.save(existingPurchaseOrder)).thenReturn(updatedPurchaseOrder);
        Mockito.when(purchaseMapper.purchaseOrderToPurchaseResponseDTO(updatedPurchaseOrder)).thenReturn(expectedPurchaseResponseDTO);

        // Act
        PurchaseResponseDTO result = purchaseService.updatePurchaseOrder(purchaseOrderId, purchaseRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPurchaseResponseDTO, result);
    }

    @Test
    @DisplayName("Purchase Order Successfully Not Updated Because Purchase Order Not Found")
    public void testUpdatePurchaseOrderOrder_shouldThrowResourceNotFoundException() {
        // Arrange
        Long purchaseOrderId = 1L;
        PurchaseRequestDTO purchaseRequestDTO = PurchaseRequestDTO.builder()
                .notes("Updated notes")
                .build();

        Mockito.when(purchaseRepository.findById(purchaseOrderId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> purchaseService.updatePurchaseOrder(purchaseOrderId, purchaseRequestDTO));
    }

    @Test
    @DisplayName("Purchase Order Deleted Successfully")
    public void testRemovePurchaseOrder_shouldCallDeletePurchaseOrderOrderOnce() {
        // Arrange
        Long purchaseOrderId = 1L;
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(purchaseOrderId);
        purchaseOrder.setPurchaseStatus(PurchaseStatus.PENDING.toString());

        Mockito.when(purchaseRepository.findById(purchaseOrderId)).thenReturn(Optional.of(purchaseOrder));

        // Act
        purchaseService.removePurchaseOrder(purchaseOrderId);

        // Assert
        Mockito.verify(purchaseRepository, Mockito.times(1)).delete(purchaseOrder);
    }

    @Test
    @DisplayName("Purchase Order Successfully Not Deleted Because Purchase Order Not Found")
    public void testRemovePurchaseOrderOrder_shouldThrowResourceNotFoundException() {
        // Arrange
        Long purchaseOrderId = 1L;

        Mockito.when(purchaseRepository.findById(purchaseOrderId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> purchaseService.removePurchaseOrder(purchaseOrderId));
    }

    @Test
    @DisplayName("Get All Purchase Orders Successfully")
    public void testGetAllPurchaseOrders_shouldReturnPageOfPurchaseResponseDTOs() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(1L);
        PurchaseResponseDTO purchaseResponseDTO = PurchaseResponseDTO.builder().id(1L).build();
        Page<PurchaseOrder> purchaseOrderPage = new PageImpl<>(Collections.singletonList(purchaseOrder), pageable, 1);

        Mockito.when(purchaseRepository.findAll(pageable)).thenReturn(purchaseOrderPage);
        Mockito.when(purchaseMapper.purchaseOrderToPurchaseResponseDTO(purchaseOrder)).thenReturn(purchaseResponseDTO);

        // Act
        Page<PurchaseResponseDTO> resultPage = purchaseService.getAllPurchaseOrders(pageable);

        // Assert
        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(purchaseResponseDTO, resultPage.getContent().get(0));
    }
}
