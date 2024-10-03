package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.ProductCreateDTO;
import com.hameed.inventario.model.dto.ProductDTO;
import com.hameed.inventario.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // for a singleton mapper
    ProductMapper  INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "primaryUom.uomCode", target = "primaryUomCode")
    ProductDTO productToProductDTO(Product product);

    Product productDTOToProduct(ProductDTO productDTO);

    Product productCreateDTOToProduct(ProductCreateDTO productCreateDTO);
}
