package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.SaleItemCreateDTO;
import com.hameed.inventario.model.dto.SaleItemDTO;
import com.hameed.inventario.model.entity.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SaleItemMapper {
    // for a singleton mapper
    SaleItemMapper  INSTANCE = Mappers.getMapper(SaleItemMapper.class);

    SaleItemDTO saleItemToSaleItemDTO(SaleItem saleItem);

    SaleItem saleItemDTOToSaleItem(SaleItemDTO saleItemDTO);

    SaleItem saleItemCreateDTOToSaleItem(SaleItemCreateDTO saleItemCreateDTO);
}
