package com.hameed.inventario;

import com.hameed.inventario.exception.DuplicateCodeException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.UnitOfMeasureMapper;
import com.hameed.inventario.model.dto.update.UnitOfMeasureDTO;
import com.hameed.inventario.model.entity.UnitOfMeasure;
import com.hameed.inventario.repository.UOMRepository;
import com.hameed.inventario.service.impl.UnitOfMeasureServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
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
    public void testAddUnitOfMeasure_shouldReturnAddedUnitOfMeasureAsUnitOfMeasureDTO() {
        //  --- Arrange ---
        UnitOfMeasureDTO unitOfMeasureDTO = createUnitOfMeasureDTO("TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1");
        UnitOfMeasure mockMappedUnitOfMeasure = createUnitOfMeasure("TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1", null);
        UnitOfMeasure mockResultUnitOfMeasure = createUnitOfMeasure("TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1", 1L);
        UnitOfMeasureDTO expectedUnitOfMeasureDTO = createUnitOfMeasureDTO(1L, "TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1");

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureMapper.unitOfMeasureDTOToUnitOfMeasure(unitOfMeasureDTO)).thenReturn(mockMappedUnitOfMeasure);
        Mockito.when(unitOfMeasureRepository.save(mockMappedUnitOfMeasure)).thenReturn(mockResultUnitOfMeasure);
        Mockito.when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureDTO(mockResultUnitOfMeasure)).thenReturn(expectedUnitOfMeasureDTO);

        // --- Act ---
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.createUnitOfMeasure(unitOfMeasureDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultUnitOfMeasureDTO, "result unitOfMeasure DTO is null"),
                () -> assertNotNull(resultUnitOfMeasureDTO.getId(), "result unitOfMeasure DTO does not include an ID"),
                () -> assertEquals(expectedUnitOfMeasureDTO, resultUnitOfMeasureDTO, "Expected unitOfMeasure DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("UnitOfMeasure successfully not Added because unitOfMeasure code already exists")
    public void testAddUnitOfMeasure_shouldThrowDuplicateCodeException() {
        //  --- Arrange ---
        UnitOfMeasureDTO unitOfMeasureDTO = createUnitOfMeasureDTO("TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1");
        UnitOfMeasure mockMappedUnitOfMeasure = createUnitOfMeasure("TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1", null);

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureMapper.unitOfMeasureDTOToUnitOfMeasure(unitOfMeasureDTO)).thenReturn(mockMappedUnitOfMeasure);
        Mockito.when(unitOfMeasureRepository.findByUomCode(mockMappedUnitOfMeasure.getUomCode())).thenReturn(Optional.of(new UnitOfMeasure()));

        // --- Act and Assert ---
        assertThrows(DuplicateCodeException.class, () -> unitOfMeasureService.createUnitOfMeasure(unitOfMeasureDTO),
                "Expected DuplicateCodeException to be thrown");
    }

    @Test
    @DisplayName("UnitOfMeasure updated successfully")
    public void testUpdateUnitOfMeasure_shouldReturnUnitOfMeasureAsUnitOfMeasureDTO() {
        // --- Arrange ---
        UnitOfMeasureDTO unitOfMeasureDTO = createUnitOfMeasureDTO(1L, "TTU02", "TestUpdateUnitOfMeasure 1", "New Testing Updating UnitOfMeasure 1");
        UnitOfMeasure existingUnitOfMeasure = createUnitOfMeasure("TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1", 1L);
        UnitOfMeasure updatedUnitOfMeasure = createUnitOfMeasure("TTU02", "TestUpdateUnitOfMeasure 1", "New Testing Updating UnitOfMeasure 1", 1L);
        UnitOfMeasureDTO expectedUnitOfMeasureDTO = createUnitOfMeasureDTO(1L, "TTU02", "TestUpdateUnitOfMeasure 1", "New Testing Updating UnitOfMeasure 1");

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureDTO.getId())).thenReturn(Optional.of(existingUnitOfMeasure));
        Mockito.when(unitOfMeasureRepository.save(existingUnitOfMeasure)).thenReturn(updatedUnitOfMeasure);
        Mockito.when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureDTO(updatedUnitOfMeasure)).thenReturn(expectedUnitOfMeasureDTO);

        // --- Act ---
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.updateUnitOfMeasure(unitOfMeasureDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultUnitOfMeasureDTO, "result updated unitOfMeasure DTO is null"),
                () -> assertNotNull(resultUnitOfMeasureDTO.getId(), "result updated unitOfMeasure DTO does not include an ID"),
                () -> assertEquals(expectedUnitOfMeasureDTO, resultUnitOfMeasureDTO, "Expected unitOfMeasure DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("UnitOfMeasure successfully not updated because id was not found")
    public void testUpdateUnitOfMeasure_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        UnitOfMeasureDTO unitOfMeasureDTO = createUnitOfMeasureDTO(1L, "TTU02", "TestUpdateUnitOfMeasure 1", "New Testing Updating UnitOfMeasure 1");

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureDTO.getId())).thenReturn(Optional.empty());


        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> unitOfMeasureService.updateUnitOfMeasure(unitOfMeasureDTO));
    }

    @Test
    @DisplayName("UnitOfMeasure deleted successfully")
    public void testDeleteUnitOfMeasure_shouldCallDeleteUnitOfMeasureOnce() {
        // --- Arrange ---
        Long unitOfMeasureId = 1L;
        UnitOfMeasure unitOfMeasure = createUnitOfMeasure("TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1", 1L);

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.of(unitOfMeasure));

        // --- Act ---
        unitOfMeasureService.deleteUnitOfMeasure(unitOfMeasureId);

        // --- Assert ---
        Mockito.verify(unitOfMeasureRepository, Mockito.times(1)).delete(unitOfMeasure);
    }

    @Test
    @DisplayName("UnitOfMeasure successfully not deleted because unitOfMeasure was not found")
    public void testDeleteUnitOfMeasure_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long unitOfMeasureId = 1L;


        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> unitOfMeasureService.deleteUnitOfMeasure(unitOfMeasureId), "Expected ResourceNotFoundException to be thrown");
    }

    @Test
    @DisplayName("UnitOfMeasure retrieved successfully")
    public void testGetUnitOfMeasure_shouldReturnUnitOfMeasureAsUnitOfMeasureDTO() {
        //  --- Arrange ---
        Long unitOfMeasureId = 1L;
        UnitOfMeasure mockResultUnitOfMeasure = createUnitOfMeasure("TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1", 1L);
        UnitOfMeasureDTO expectedUnitOfMeasureDTO = createUnitOfMeasureDTO(1L, "TT01", "TestUnitOfMeasure 1", "New Testing UnitOfMeasure 1");

        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.of(mockResultUnitOfMeasure));
        Mockito.when(unitOfMeasureMapper.unitOfMeasureToUnitOfMeasureDTO(mockResultUnitOfMeasure)).thenReturn(expectedUnitOfMeasureDTO);

        // --- Act ---
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.getUnitOfMeasureById(unitOfMeasureId);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultUnitOfMeasureDTO, "result unitOfMeasure DTO is null"),
                () -> assertNotNull(resultUnitOfMeasureDTO.getId(), "result unitOfMeasure DTO does not include an ID"),
                () -> assertEquals(expectedUnitOfMeasureDTO, resultUnitOfMeasureDTO, "Expected unitOfMeasure DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("UnitOfMeasure successfully not retrieved because unitOfMeasure was not found")
    public void testGetUnitOfMeasure_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long unitOfMeasureId = 1L;


        // Define behavior of the mocks
        Mockito.when(unitOfMeasureRepository.findById(unitOfMeasureId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> unitOfMeasureService.getUnitOfMeasureById(unitOfMeasureId), "Expected ResourceNotFoundException to be thrown");
    }



    // some utility builder methods
    private UnitOfMeasureDTO createUnitOfMeasureDTO(String code, String name, String description) {
        UnitOfMeasureDTO dto = new UnitOfMeasureDTO();
        dto.setUomCode(code);
        dto.setUomName(name);
        dto.setDescription(description);
        return dto;
    }

    private UnitOfMeasure createUnitOfMeasure(String code, String name, String description, Long id) {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setUomCode(code);
        unitOfMeasure.setUomName(name);
        unitOfMeasure.setDescription(description);
        unitOfMeasure.setProducts(new HashSet<>());
        if (id != null) {
            unitOfMeasure.setId(id);
        }
        return unitOfMeasure;
    }

    private UnitOfMeasureDTO createUnitOfMeasureDTO(Long id, String code, String name, String description) {
        UnitOfMeasureDTO dto = createUnitOfMeasureDTO(code, name, description);
        dto.setId(id);
        return dto;
    }
}
