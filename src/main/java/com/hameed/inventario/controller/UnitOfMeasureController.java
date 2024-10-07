package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.update.UnitOfMeasureDTO;
import com.hameed.inventario.service.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/unit-of-measures")
public class UnitOfMeasureController {

    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public UnitOfMeasureController(UnitOfMeasureService unitOfMeasureService) {
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<UnitOfMeasureDTO>> getAllUnitOfMeasures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UnitOfMeasureDTO> unitOfMeasureDTOPage = unitOfMeasureService.getAllUnitOfMeasures(PageRequest.of(page, size));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "UnitOfMeasures Retrieved Successfully", unitOfMeasureDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UnitOfMeasureDTO>> getUnitOfMeasureById(@PathVariable Long id) {
        UnitOfMeasureDTO unitOfMeasureDTO = unitOfMeasureService.getUnitOfMeasureById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "UnitOfMeasure Retrieved Successfully", unitOfMeasureDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<UnitOfMeasureDTO>> addUnitOfMeasure(@RequestBody UnitOfMeasureDTO unitOfMeasureDTO) {
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.createUnitOfMeasure(unitOfMeasureDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "UnitOfMeasure Created Successfully", resultUnitOfMeasureDTO));  // 201 CREATED
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<UnitOfMeasureDTO>> updateUnitOfMeasure(@RequestBody UnitOfMeasureDTO unitOfMeasureDTO) {
        UnitOfMeasureDTO resultUnitOfMeasureDTO = unitOfMeasureService.updateUnitOfMeasure(unitOfMeasureDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "UnitOfMeasure Updated Successfully", resultUnitOfMeasureDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<UnitOfMeasureDTO>> deleteUnitOfMeasure(@PathVariable Long id) {
        unitOfMeasureService.deleteUnitOfMeasure(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>(204, "UnitOfMeasure Deleted Successfully"));  // 204 NO_CONTENT
    }
}
