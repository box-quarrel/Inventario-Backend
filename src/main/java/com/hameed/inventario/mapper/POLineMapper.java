package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.request.POLineRequestDTO;
import com.hameed.inventario.model.dto.response.POLineResponseDTO;
import com.hameed.inventario.model.entity.PurchaseLine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface POLineMapper {
    // for a singleton mapper
//    POLineMapper  INSTANCE = Mappers.getMapper(POLineMapper.class);

    POLineResponseDTO purchaseLineToPOLineResponseDTO(PurchaseLine purchaseLine);

    PurchaseLine poLineResponseDTOToPurchaseLine(POLineResponseDTO poLineResponseDTO);

    PurchaseLine poLineRequestDTOToPurchaseLine(POLineRequestDTO poLineRequestDTO);
}
