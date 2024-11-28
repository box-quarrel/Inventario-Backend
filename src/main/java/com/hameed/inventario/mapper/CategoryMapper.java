package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.basic.CategoryDTO;
import com.hameed.inventario.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // for a singleton mapper
//    CategoryMapper  INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
