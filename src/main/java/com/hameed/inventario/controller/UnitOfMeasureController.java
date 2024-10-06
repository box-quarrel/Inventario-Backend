package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.update.UnitOfMeasureDTO;
import com.hameed.inventario.service.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<UnitOfMeasureDTO> getAllUnitOfMeasures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return unitOfMeasureService.getAllUnitOfMeasures(pageable);
    }


    @GetMapping("/{id}")
    public UnitOfMeasureDTO getUnitOfMeasureById(@PathVariable Long id) {
        return unitOfMeasureService.getUnitOfMeasureById(id);
    }

    @PostMapping
    public void addUnitOfMeasure(@RequestBody UnitOfMeasureDTO unitOfMeasureDTO) {
        unitOfMeasureService.createUnitOfMeasure(unitOfMeasureDTO);
    }

    @PutMapping
    public void updateUnitOfMeasure(@RequestBody UnitOfMeasureDTO unitOfMeasureDTO) {
        unitOfMeasureService.updateUnitOfMeasure(unitOfMeasureDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUnitOfMeasure(@PathVariable Long id) {
        unitOfMeasureService.deleteUnitOfMeasure(id);
    }


}
