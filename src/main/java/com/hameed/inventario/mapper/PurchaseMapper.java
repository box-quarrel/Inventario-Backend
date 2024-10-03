package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.PurchaseCreateDTO;
import com.hameed.inventario.model.dto.PurchaseDTO;
import com.hameed.inventario.model.entity.PurchaseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    // for a singleton mapper
    PurchaseMapper  INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    @Mapping(source = "supplier.supplierName", target = "supplierName")
    PurchaseDTO purchaseOrderToPurchaseDTO(PurchaseOrder purchase);

    PurchaseOrder purchaseDTOTopurchaseOrder(PurchaseDTO purchaseDTO);

    PurchaseOrder purchaseCreateDTOToPurchaseOrder(PurchaseCreateDTO purchaseCreateDTO);
}
