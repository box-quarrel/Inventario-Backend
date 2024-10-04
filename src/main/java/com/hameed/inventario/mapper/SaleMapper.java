package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.create.SaleCreateDTO;
import com.hameed.inventario.model.dto.update.SaleDTO;
import com.hameed.inventario.model.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    // for a singleton mapper
    SaleMapper  INSTANCE = Mappers.getMapper(SaleMapper.class);

    SaleDTO saleToSaleDTO(Sale sale);

    Sale saleDTOToSale(SaleDTO saleDTO);

    Sale saleCreateDTOToSale(SaleCreateDTO saleCreateDTO);
}
