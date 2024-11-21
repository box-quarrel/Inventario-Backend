package com.hameed.inventario.unit.service;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.SupplierMapper;
import com.hameed.inventario.model.dto.update.SupplierDTO;
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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

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
        SupplierDTO supplierDTO = createSupplierDTO("Amazon", "test", "777-888-2222", "test@example.com", "303 Business St, Commerce City");
        Supplier mockMappedSupplier = createSupplier("Amazon", "test", "777-888-2222", "test@example.com", "303 Business St, Commerce City", null);
        Supplier mockResultSupplier = createSupplier("Amazon", "test", "777-888-2222", "test@example.com", "303 Business St, Commerce City", 1L);
        SupplierDTO expectedSupplierDTO = createSupplierDTO(1L, "Amazon", "Test", "777-888-2222", "test@example.com", "303 Business St, Commerce City");

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
        SupplierDTO supplierDTO = createSupplierDTO(1L, "Amazon", "Test", "777-888-2222", "test@example.com", "303 Business St, Commerce City");
        Supplier existingSupplier = createSupplier("Amazon", "Test", "777-888-2222", "test@example.com", "303 Business St, Commerce City", 1L);
        Supplier updatedSupplier = createSupplier("Amazon", "Test", "777-888-2222", "test@example.com", "303 Business St, Commerce City", 1L);
        SupplierDTO expectedSupplierDTO = createSupplierDTO(1L, "Amazon", "Test", "777-888-2222", "test@example.com", "303 Business St, Commerce City");

        // Define behavior of the mocks
        Mockito.when(supplierRepository.findById(supplierDTO.getId())).thenReturn(Optional.of(existingSupplier));
        Mockito.when(supplierRepository.save(existingSupplier)).thenReturn(updatedSupplier);
        Mockito.when(supplierMapper.supplierToSupplierDTO(updatedSupplier)).thenReturn(expectedSupplierDTO);

        // --- Act ---
        SupplierDTO resultSupplierDTO = supplierService.updateSupplier(supplierDTO);

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
        SupplierDTO supplierDTO = createSupplierDTO(1L, "Amazon", "Test", "777-888-2222", "test@example.com", "303 Business St, Commerce City");

        // Define behavior of the mocks
        Mockito.when(supplierRepository.findById(supplierDTO.getId())).thenReturn(Optional.empty());


        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> supplierService.updateSupplier(supplierDTO));
    }

    @Test
    @DisplayName("Supplier deleted successfully")
    public void testDeleteSupplier_shouldCallDeleteSupplierOnce() {
        // --- Arrange ---
        Long supplierId = 1L;
        Supplier supplier = createSupplier("Amazon", "Test", "777-888-2222", "test@example.com", "303 Business St, Commerce City", 1L);

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
        Supplier mockResultSupplier = createSupplier("Amazon", "Test", "777-888-2222", "test@example.com", "303 Business St, Commerce City", 1L);
        SupplierDTO expectedSupplierDTO = createSupplierDTO(1L, "Amazon", "Test", "777-888-2222", "test@example.com", "303 Business St, Commerce City");

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



    // some utility builder methods
    private SupplierDTO createSupplierDTO(String name, String contactName, String contactPhone, String email, String address) {
        SupplierDTO dto = new SupplierDTO();
        dto.setSupplierName(name);
        dto.setContactName(contactName);
        dto.setContactPhone(contactPhone);
        dto.setEmail(email);
        dto.setAddress(address);
        return dto;
    }

    private Supplier createSupplier(String name, String contactName, String contactPhone, String email, String address, Long id) {
        Supplier supplier = new Supplier();
        supplier.setSupplierName(name);
        supplier.setContactName(contactName);
        supplier.setContactPhone(contactPhone);
        supplier.setEmail(email);
        supplier.setAddress(address);
        supplier.setProducts(new HashSet<>());
        supplier.setPurchaseOrders(new ArrayList<>());
        if (id != null) {
            supplier.setId(id);
        }
        return supplier;
    }

    private SupplierDTO createSupplierDTO(Long id, String name, String contactName, String contactPhone, String email, String address) {
        SupplierDTO dto = createSupplierDTO(name, contactName, contactPhone, email, address);
        dto.setId(id);
        return dto;
    }
}
