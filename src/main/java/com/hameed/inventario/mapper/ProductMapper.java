package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.request.ProductRequestDTO;
import com.hameed.inventario.model.dto.response.ProductResponseDTO;
import com.hameed.inventario.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // for a singleton mapper
//    ProductMapper  INSTANCE = Mappers.getMapper(ProductMapper.class);


    ProductResponseDTO productToProductResponseDTO(Product product);

    Product ProductResponseDTOToProduct(ProductResponseDTO productResponseDTO);

    Product ProductRequestDTOToProduct(ProductRequestDTO productRequestDTO);
}
