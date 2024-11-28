package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.request.PurchaseRequestDTO;
import com.hameed.inventario.model.dto.response.PurchaseResponseDTO;
import com.hameed.inventario.model.entity.PurchaseOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    // for a singleton mapper
//    PurchaseMapper  INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    PurchaseResponseDTO purchaseOrderToPurchaseResponseDTO(PurchaseOrder purchase);

    PurchaseOrder PurchaseResponseDTOTopurchaseOrder(PurchaseResponseDTO purchaseResponseDTO);

    PurchaseOrder PurchaseRequestDTOToPurchaseOrder(PurchaseRequestDTO purchaseRequestDTO);
}
