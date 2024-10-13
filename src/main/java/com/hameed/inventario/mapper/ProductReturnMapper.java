package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.create.ProductReturnCreateDTO;
import com.hameed.inventario.model.dto.update.ProductReturnDTO;
import com.hameed.inventario.model.entity.ProductReturn;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductReturnMapper {
    // for a singleton mapper
//    ProductReturnMapper  INSTANCE = Mappers.getMapper(ProductReturnMapper.class);


    ProductReturnDTO productReturnToProductReturnDTO(ProductReturn productReturn);

    ProductReturn productReturnDTOToProductReturn(ProductReturnDTO productReturnDTO);

    ProductReturn productReturnCreateDTOToProductReturn(ProductReturnCreateDTO productReturnCreateDTO);
}
