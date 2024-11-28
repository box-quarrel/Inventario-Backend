package com.hameed.inventario.unit.service;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.SupplierMapper;
import com.hameed.inventario.model.dto.basic.SupplierDTO;
import com.hameed.inventario.model.entity.Supplier;
import com.hameed.inventario.repository.SupplierRepository;
import com.hameed.inventario.service.impl.SupplierServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    @DisplayName("Supplier Added Successfully")
    public void testAddSupplier_shouldReturnAddedSupplierAsSupplierDTO() {
        //  --- Arrange ---
        // Using builder pattern to create SupplierDTO without id for initial creation
        SupplierDTO supplierDTO = SupplierDTO.builder()
                .supplierName("Amazon")
                .contactName("test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();

// Using builder pattern to create Supplier without id (for mockMappedSupplier)
        Supplier mockMappedSupplier = Supplier.builder()
                .supplierName("Amazon")
                .contactName("test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();
        mockMappedSupplier.setId(null);  // Set the id as null for mockMappedSupplier

// Using builder pattern to create Supplier with id (for mockResultSupplier)
        Supplier mockResultSupplier = Supplier.builder()
                .supplierName("Amazon")
                .contactName("test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();
        mockResultSupplier.setId(1L);  // Set the id for mockResultSupplier

// Using builder pattern to create SupplierDTO with id for expected result
        SupplierDTO expectedSupplierDTO = SupplierDTO.builder()
                .id(1L)
                .supplierName("Amazon")
                .contactName("Test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();


        // Define behavior of the mocks
        Mockito.when(supplierMapper.supplierDTOToSupplier(supplierDTO)).thenReturn(mockMappedSupplier);
        Mockito.when(supplierRepository.save(mockMappedSupplier)).thenReturn(mockResultSupplier);
        Mockito.when(supplierMapper.supplierToSupplierDTO(mockResultSupplier)).thenReturn(expectedSupplierDTO);

        // --- Act ---
        SupplierDTO resultSupplierDTO = supplierService.addSupplier(supplierDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultSupplierDTO, "result supplier DTO is null"),
                () -> assertNotNull(resultSupplierDTO.getId(), "result supplier DTO does not include an ID"),
                () -> assertEquals(expectedSupplierDTO, resultSupplierDTO, "Expected supplier DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("Supplier updated successfully")
    public void testUpdateSupplier_shouldReturnSupplierAsSupplierDTO() {
        // --- Arrange ---
        Long supplierId = 1L;

// Using builder pattern to create SupplierDTO without id for initial creation
        SupplierDTO supplierDTO = SupplierDTO.builder()
                .supplierName("Amazon")
                .contactName("Test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();

// Using builder pattern to create Supplier with id for existingSupplier
        Supplier existingSupplier = Supplier.builder()
                .supplierName("Amazon")
                .contactName("Test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();
        existingSupplier.setId(supplierId);  // Set the id after creation

// Using builder pattern to create Supplier with id for updatedSupplier
        Supplier updatedSupplier = Supplier.builder()
                .supplierName("Amazon")
                .contactName("Test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();
        updatedSupplier.setId(supplierId);  // Set the id after creation

// Using builder pattern to create SupplierDTO with id for expected result
        SupplierDTO expectedSupplierDTO = SupplierDTO.builder()
                .id(supplierId)
                .supplierName("Amazon")
                .contactName("Test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();


        // Define behavior of the mocks
        Mockito.when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(existingSupplier));
        Mockito.when(supplierRepository.save(existingSupplier)).thenReturn(updatedSupplier);
        Mockito.when(supplierMapper.supplierToSupplierDTO(updatedSupplier)).thenReturn(expectedSupplierDTO);

        // --- Act ---
        SupplierDTO resultSupplierDTO = supplierService.updateSupplier(supplierId, supplierDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultSupplierDTO, "result updated supplier DTO is null"),
                () -> assertNotNull(resultSupplierDTO.getId(), "result updated supplier DTO does not include an ID"),
                () -> assertEquals(expectedSupplierDTO, resultSupplierDTO, "Expected supplier DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("Supplier successfully not updated because id was not found")
    public void testUpdateSupplier_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long supplierId = 1L;
        SupplierDTO supplierDTO = SupplierDTO.builder()
                .supplierName("Amazon")
                .contactName("Test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();

        // Define behavior of the mocks
        Mockito.when(supplierRepository.findById(supplierId)).thenReturn(Optional.empty());


        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> supplierService.updateSupplier(supplierId, supplierDTO));
    }

    @Test
    @DisplayName("Supplier deleted successfully")
    public void testDeleteSupplier_shouldCallDeleteSupplierOnce() {
        // --- Arrange ---
        Long supplierId = 1L;

        Supplier supplier = Supplier.builder()
                .supplierName("Amazon")
                .contactName("Test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .purchaseOrders(new ArrayList<>())
                .products(new HashSet<>())
                .build();
        supplier.setId(supplierId);  // Set the id after using the builder


        // Define behavior of the mocks
        Mockito.when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));

        // --- Act ---
        supplierService.deleteSupplier(supplierId);

        // --- Assert ---
        Mockito.verify(supplierRepository, Mockito.times(1)).delete(supplier);
    }

    @Test
    @DisplayName("Supplier successfully not deleted because supplier was not found")
    public void testDeleteSupplier_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long supplierId = 1L;


        // Define behavior of the mocks
        Mockito.when(supplierRepository.findById(supplierId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> supplierService.deleteSupplier(supplierId), "Expected ResourceNotFoundException to be thrown");
    }

    @Test
    @DisplayName("Supplier retrieved successfully")
    public void testGetSupplier_shouldReturnSupplierAsSupplierDTO() {
        //  --- Arrange ---
        Long supplierId = 1L;

// Using builder pattern to create Supplier with id
        Supplier mockResultSupplier = Supplier.builder()
                .supplierName("Amazon")
                .contactName("Test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")
                .build();
        mockResultSupplier.setId(supplierId);  // Set the id after creation

// Using builder pattern to create SupplierDTO with id
        SupplierDTO expectedSupplierDTO = SupplierDTO.builder()
                .id(supplierId)
                .supplierName("Amazon")
                .contactName("Test")
                .contactPhone("777-888-2222")
                .email("test@example.com")
                .address("303 Business St, Commerce City")

                .build();


        // Define behavior of the mocks
        Mockito.when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(mockResultSupplier));
        Mockito.when(supplierMapper.supplierToSupplierDTO(mockResultSupplier)).thenReturn(expectedSupplierDTO);

        // --- Act ---
        SupplierDTO resultSupplierDTO = supplierService.getSupplierById(supplierId);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultSupplierDTO, "result supplier DTO is null"),
                () -> assertNotNull(resultSupplierDTO.getId(), "result supplier DTO does not include an ID"),
                () -> assertEquals(expectedSupplierDTO, resultSupplierDTO, "Expected supplier DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("Supplier successfully not retrieved because supplier was not found")
    public void testGetSupplier_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long supplierId = 1L;


        // Define behavior of the mocks
        Mockito.when(supplierRepository.findById(supplierId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> supplierService.getSupplierById(supplierId), "Expected ResourceNotFoundException to be thrown");
    }
}
