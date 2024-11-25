package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.create.SaleItemCreateDTO;
import com.hameed.inventario.model.dto.update.SaleItemDTO;
import com.hameed.inventario.model.entity.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {
    // for a singleton mapper
//    SaleItemMapper  INSTANCE = Mappers.getMapper(SaleItemMapper.class);

    SaleItemDTO saleItemToSaleItemDTO(SaleItem saleItem);

    SaleItem saleItemDTOToSaleItem(SaleItemDTO saleItemDTO);

    SaleItem saleItemCreateDTOToSaleItem(SaleItemCreateDTO saleItemCreateDTO);
}
