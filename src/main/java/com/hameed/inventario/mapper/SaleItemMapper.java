package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.request.SaleItemRequestDTO;
import com.hameed.inventario.model.dto.response.SaleItemResponseDTO;
import com.hameed.inventario.model.entity.SaleItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {
    // for a singleton mapper
//    SaleItemMapper  INSTANCE = Mappers.getMapper(SaleItemMapper.class);

    SaleItemResponseDTO saleItemToSaleItemResponseDTO(SaleItem saleItem);

    SaleItem SaleItemResponseDTOToSaleItem(SaleItemResponseDTO saleItemResponseDTO);

    SaleItem SaleItemRequestDTOToSaleItem(SaleItemRequestDTO saleItemRequestDTO);
}
