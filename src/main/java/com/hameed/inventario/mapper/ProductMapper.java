package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.create.ProductCreateDTO;
import com.hameed.inventario.model.dto.update.ProductDTO;
import com.hameed.inventario.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // for a singleton mapper
//    ProductMapper  INSTANCE = Mappers.getMapper(ProductMapper.class);


    ProductDTO productToProductDTO(Product product);

    Product productDTOToProduct(ProductDTO productDTO);

    Product productCreateDTOToProduct(ProductCreateDTO productCreateDTO);
}
