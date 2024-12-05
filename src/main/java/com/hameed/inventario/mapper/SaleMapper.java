package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.request.SaleRequestDTO;
import com.hameed.inventario.model.dto.response.SaleResponseDTO;
import com.hameed.inventario.model.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    // for a singleton mapper
//    SaleMapper  INSTANCE = Mappers.getMapper(SaleMapper.class);


    SaleResponseDTO saleToSaleResponseDTO(Sale sale);

    Sale saleResponseDTOToSale(SaleResponseDTO saleResponseDTO);

    Sale saleRequestDTOToSale(SaleRequestDTO saleRequestDTO);
}
