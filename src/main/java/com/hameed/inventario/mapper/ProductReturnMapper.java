package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.request.ProductReturnRequestDTO;
import com.hameed.inventario.model.dto.response.ProductReturnResponseDTO;
import com.hameed.inventario.model.entity.ProductReturn;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductReturnMapper {
    // for a singleton mapper
//    ProductReturnMapper  INSTANCE = Mappers.getMapper(ProductReturnMapper.class);


    ProductReturnResponseDTO productReturnToProductReturnResponseDTO(ProductReturn productReturn);

    ProductReturn ProductReturnResponseDTOToProductReturn(ProductReturnResponseDTO productReturnResponseDTO);

    ProductReturn ProductReturnRequestDTOToProductReturn(ProductReturnRequestDTO productReturnRequestDTO);
}
