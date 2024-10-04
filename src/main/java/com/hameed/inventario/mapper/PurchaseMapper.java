package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.create.PurchaseCreateDTO;
import com.hameed.inventario.model.dto.update.PurchaseDTO;
import com.hameed.inventario.model.entity.PurchaseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    // for a singleton mapper
    PurchaseMapper  INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    PurchaseDTO purchaseOrderToPurchaseDTO(PurchaseOrder purchase);

    PurchaseOrder purchaseDTOTopurchaseOrder(PurchaseDTO purchaseDTO);

    PurchaseOrder purchaseCreateDTOToPurchaseOrder(PurchaseCreateDTO purchaseCreateDTO);
}
