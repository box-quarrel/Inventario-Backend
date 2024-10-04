package com.hameed.inventario.mapper;



import com.hameed.inventario.model.dto.update.UnitOfMeasureDTO;
import com.hameed.inventario.model.entity.UnitOfMeasure;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UnitOfMeasureMapper {
    // for a singleton mapper
    UnitOfMeasureMapper  INSTANCE = Mappers.getMapper(UnitOfMeasureMapper.class);

    UnitOfMeasureDTO unitOfMeasureToUnitOfMeasureDTO(UnitOfMeasure unitOfMeasure);

    UnitOfMeasure unitOfMeasureDTOToUnitOfMeasure(UnitOfMeasureDTO unitOfMeasureDTO);
}
