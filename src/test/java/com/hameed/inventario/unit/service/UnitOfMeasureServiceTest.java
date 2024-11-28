package com.hameed.inventario.unit.service;

import com.hameed.inventario.exception.DuplicateCodeException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.UnitOfMeasureMapper;
import com.hameed.inventario.model.dto.basic.UnitOfMeasureDTO;
import com.hameed.inventario.model.entity.UnitOfMeasure;
import com.hameed.inventario.repository.UOMRepository;
import com.hameed.inventario.service.impl.UnitOfMeasureServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
public class UnitOfMeasureServiceTest {

    @Mock
    private UOMRepository unitOfMeasureRepository;
    @Mock
    private UnitOfMeasureMapper unitOfMeasureMapper;

    @InjectMocks
    private UnitOfMeasureServiceImpl unitOfMeasureService;

    @Test
    @DisplayName("UnitOfMeasure Added Successfully")
    public void testCreateUnitOfMeasure_shouldReturnAddedUnitOfMeasureAsDTO() {
        // --- Arrange ---
        UnitOfMeasureDTO unitOfMeasureDTO = UnitOfMeasureDTO.builder()
                .uomCode("UOM01")
                .uomName("Kilogram")
                .description("Unit for mass")
                .build();

        UnitOfMeasure mockMappedUnitOfMeasure = UnitOfMeasure.builder()
                .uomCode("UOM01")
                .uomName("Kilogram")
                .description("Unit for mass")
                .build();

        UnitOfMeasure mockResultUnitOfMeasure = UnitOfMeasure.builder()
                .uomCode("UOM01")
                .uomName("Kilogram")
                .description("Unit for mass")
                .build();
        mockResultUnitOfMeasure.setId(1L);

        UnitOfMeasureDTO expectedUnitOfMeasureDTO = UnitOfMeasureDTO.builder()
                .id(1L)
                .uomCode("UOM01")
                .uomName("Kilogram")
                .description("Unit for mass")
                .build();

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureMapper.unitOfMeasureDTOToUnitOfMeasure(unitOfMeasureDTO)).thenReturn(mockMappedUnitOfMeasure);
        Mockito.when(unitOfMeasureRepository.save(mockMappedUnitOfMeasure)).thenReturn(mockResultUnitOfMeasure);
        Mockito.when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureDTO(mockResultUnitOfMeasure)).thenReturn(expectedUnitOfMeasureDTO);

        // --- Act ---
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.createUnitOfMeasure(unitOfMeasureDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultUnitOfMeasureDTO, "result unit of measure DTO is null"),
                () -> assertNotNull(resultUnitOfMeasureDTO.getId(), "result unit of measure DTO does not include an ID"),
                () -> assertEquals(expectedUnitOfMeasureDTO, resultUnitOfMeasureDTO, "Expected unit of measure DTO is not correct")
        );
    }

    @Test
    @DisplayName("UnitOfMeasure not Added due to duplicate code")
    public void testCreateUnitOfMeasure_shouldThrowDuplicateCodeException() {
        // --- Arrange ---
        UnitOfMeasureDTO unitOfMeasureDTO = UnitOfMeasureDTO.builder()
                .uomCode("UOM01")
                .uomName("Kilogram")
                .description("Unit for mass")
                .build();

        UnitOfMeasure mockMappedUnitOfMeasure = UnitOfMeasure.builder()
                .uomCode("UOM01")
                .uomName("Kilogram")
                .description("Unit for mass")
                .build();

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureMapper.unitOfMeasureDTOToUnitOfMeasure(unitOfMeasureDTO)).thenReturn(mockMappedUnitOfMeasure);
        Mockito.when(unitOfMeasureRepository.findByUomCode(mockMappedUnitOfMeasure.getUomCode())).thenReturn(Optional.of(new UnitOfMeasure()));

        // --- Act and Assert ---
        assertThrows(DuplicateCodeException.class, () -> unitOfMeasureService.createUnitOfMeasure(unitOfMeasureDTO),
                "Expected DuplicateCodeException to be thrown");
    }

    @Test
    @DisplayName("UnitOfMeasure Updated Successfully")
    public void testUpdateUnitOfMeasure_shouldReturnUpdatedUnitOfMeasureAsDTO() {
        // --- Arrange ---
        Long unitOfMeasureId = 1L;

        UnitOfMeasureDTO unitOfMeasureDTO = UnitOfMeasureDTO.builder()
                .uomCode("UOM02")
                .uomName("Gram")
                .description("Unit for small masses")
                .build();

        UnitOfMeasure existingUnitOfMeasure = UnitOfMeasure.builder()
                .uomCode("UOM01")
                .uomName("Kilogram")
                .description("Unit for mass")
                .build();
        existingUnitOfMeasure.setId(unitOfMeasureId);

        UnitOfMeasure updatedUnitOfMeasure = UnitOfMeasure.builder()
                .uomCode("UOM02")
                .uomName("Gram")
                .description("Unit for small masses")
                .build();
        updatedUnitOfMeasure.setId(unitOfMeasureId);

        UnitOfMeasureDTO expectedUnitOfMeasureDTO = UnitOfMeasureDTO.builder()
                .id(unitOfMeasureId)
                .uomCode("UOM02")
                .uomName("Gram")
                .description("Unit for small masses")
                .build();

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.of(existingUnitOfMeasure));
        Mockito.when(unitOfMeasureRepository.save(existingUnitOfMeasure)).thenReturn(updatedUnitOfMeasure);
        Mockito.when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureDTO(updatedUnitOfMeasure)).thenReturn(expectedUnitOfMeasureDTO);

        // --- Act ---
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.updateUnitOfMeasure(unitOfMeasureId, unitOfMeasureDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultUnitOfMeasureDTO, "result updated unit of measure DTO is null"),
                () -> assertNotNull(resultUnitOfMeasureDTO.getId(), "result updated unit of measure DTO does not include an ID"),
                () -> assertEquals(expectedUnitOfMeasureDTO, resultUnitOfMeasureDTO, "Expected unit of measure DTO is not correct")
        );
    }

    @Test
    @DisplayName("UnitOfMeasure not updated due to ID not found")
    public void testUpdateUnitOfMeasure_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long unitOfMeasureId = 1L;

        UnitOfMeasureDTO unitOfMeasureDTO = UnitOfMeasureDTO.builder()
                .uomCode("UOM02")
                .uomName("Gram")
                .description("Unit for small masses")
                .build();

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> unitOfMeasureService.updateUnitOfMeasure(unitOfMeasureId, unitOfMeasureDTO));
    }

    @Test
    @DisplayName("UnitOfMeasure Deleted Successfully")
    public void testDeleteUnitOfMeasure_shouldCallDeleteOnce() {
        // --- Arrange ---
        Long unitOfMeasureId = 1L;
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
                .uomCode("UOM02")
                .uomName("Gram")
                .description("Unit for small masses")
                .products(new HashSet<>())
                .build();
        unitOfMeasure.setId(unitOfMeasureId);

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.of(unitOfMeasure));

        // --- Act ---
        unitOfMeasureService.deleteUnitOfMeasure(unitOfMeasureId);

        // --- Assert ---
        Mockito.verify(unitOfMeasureRepository, Mockito.times(1)).delete(unitOfMeasure);
    }

    @Test
    @DisplayName("UnitOfMeasure not deleted because it was not found")
    public void testDeleteUnitOfMeasure_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long unitOfMeasureId = 1L;

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> unitOfMeasureService.deleteUnitOfMeasure(unitOfMeasureId));
    }

    @Test
    @DisplayName("UnitOfMeasure Retrieved Successfully")
    public void testGetUnitOfMeasure_shouldReturnUnitOfMeasureAsDTO() {
        // --- Arrange ---
        Long unitOfMeasureId = 1L;

        UnitOfMeasure mockResultUnitOfMeasure = UnitOfMeasure.builder()
                .uomCode("UOM01")
                .uomName("Kilogram")
                .description("Unit for mass")
                .build();
        mockResultUnitOfMeasure.setId(unitOfMeasureId);

        UnitOfMeasureDTO expectedUnitOfMeasureDTO = UnitOfMeasureDTO.builder()
                .id(unitOfMeasureId)
                .uomCode("UOM01")
                .uomName("Kilogram")
                .description("Unit for mass")
                .build();

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.of(mockResultUnitOfMeasure));
        Mockito.when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureDTO(mockResultUnitOfMeasure)).thenReturn(expectedUnitOfMeasureDTO);

        // --- Act ---
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.getUnitOfMeasureById(unitOfMeasureId);

        // --- Assert ---
        assertEquals(expectedUnitOfMeasureDTO, resultUnitOfMeasureDTO, "Expected unit of measure DTO is not correct");
    }

    @Test
    @DisplayName("UnitOfMeasure not found by ID")
    public void testGetUnitOfMeasure_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long unitOfMeasureId = 1L;

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> unitOfMeasureService.getUnitOfMeasureById(unitOfMeasureId));
    }
}
