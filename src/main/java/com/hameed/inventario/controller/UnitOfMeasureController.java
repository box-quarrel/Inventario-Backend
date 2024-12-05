package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.basic.UnitOfMeasureDTO;
import com.hameed.inventario.service.UnitOfMeasureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/unit-of-measures")
public class UnitOfMeasureController {

    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public UnitOfMeasureController(UnitOfMeasureService unitOfMeasureService) {
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<UnitOfMeasureDTO>> getAllUnitOfMeasures(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<UnitOfMeasureDTO> unitOfMeasureDTOPage = unitOfMeasureService.getAllUnitOfMeasures(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "UnitOfMeasures Retrieved Successfully", unitOfMeasureDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UnitOfMeasureDTO>> getUnitOfMeasureById(@PathVariable Long id) {
        UnitOfMeasureDTO unitOfMeasureDTO = unitOfMeasureService.getUnitOfMeasureById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "UnitOfMeasure Retrieved Successfully", unitOfMeasureDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<UnitOfMeasureDTO>> addUnitOfMeasure(@Valid @RequestBody UnitOfMeasureDTO unitOfMeasureDTO) {
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.createUnitOfMeasure(unitOfMeasureDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "UnitOfMeasure Created Successfully", resultUnitOfMeasureDTO));  // 201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UnitOfMeasureDTO>> updateUnitOfMeasure(@PathVariable Long id, @Valid @RequestBody UnitOfMeasureDTO unitOfMeasureDTO) {
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.updateUnitOfMeasure(id, unitOfMeasureDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "UnitOfMeasure Updated Successfully", resultUnitOfMeasureDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<UnitOfMeasureDTO>> deleteUnitOfMeasure(@PathVariable Long id) {
        unitOfMeasureService.deleteUnitOfMeasure(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
