package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.CategoryDTO;
import com.hameed.inventario.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // for a singleton mapper
    CategoryMapper  INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
