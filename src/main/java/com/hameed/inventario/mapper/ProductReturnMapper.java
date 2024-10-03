package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.ProductReturnCreateDTO;
import com.hameed.inventario.model.dto.ProductReturnDTO;
import com.hameed.inventario.model.entity.ProductReturn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductReturnMapper {
    // for a singleton mapper
    ProductReturnMapper  INSTANCE = Mappers.getMapper(ProductReturnMapper.class);

    @Mapping(source = "customer.customerName", target = "customerName")
    @Mapping(source = "product.productName", target = "productName")
    ProductReturnDTO productReturnToProductReturnDTO(ProductReturn productReturn);

    ProductReturn productReturnDTOToProductReturn(ProductReturnDTO productReturnDTO);

    ProductReturn productReturnCreateDTOToProductReturn(ProductReturnCreateDTO productReturnCreateDTO);
}
