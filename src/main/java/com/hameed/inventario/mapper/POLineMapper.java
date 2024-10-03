package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.POLineCreateDTO;
import com.hameed.inventario.model.dto.POLineDTO;
import com.hameed.inventario.model.entity.PurchaseLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface POLineMapper {
    // for a singleton mapper
    POLineMapper  INSTANCE = Mappers.getMapper(POLineMapper.class);

    @Mapping(source = "product.productName", target = "productName")
    POLineDTO purchaseLineToPOLineDTO(PurchaseLine purchaseLine);

    PurchaseLine poLineDTOToPurchaseLine(POLineDTO poLineDTO);

    PurchaseLine poLineCreateDTOToPurchaseLine(POLineCreateDTO poLineCreateDTO);
}
