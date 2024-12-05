package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.request.ProductReturnRequestDTO;
import com.hameed.inventario.model.dto.response.ProductReturnResponseDTO;
import com.hameed.inventario.model.entity.ProductReturn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductReturnMapper {
    // for a singleton mapper
//    ProductReturnMapper  INSTANCE = Mappers.getMapper(ProductReturnMapper.class);


    @Mapping(source = "sale.salesNumber", target = "salesNumber")
    ProductReturnResponseDTO productReturnToProductReturnResponseDTO(ProductReturn productReturn);

    @Mapping(source = "salesNumber", target = "sale.salesNumber")
    ProductReturn ProductReturnResponseDTOToProductReturn(ProductReturnResponseDTO productReturnResponseDTO);

    ProductReturn ProductReturnRequestDTOToProductReturn(ProductReturnRequestDTO productReturnRequestDTO);
}
