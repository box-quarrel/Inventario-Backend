package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.SaleCreateDTO;
import com.hameed.inventario.model.dto.SaleDTO;
import com.hameed.inventario.model.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SaleMapper {
    // for a singleton mapper
    SaleMapper  INSTANCE = Mappers.getMapper(SaleMapper.class);

    @Mapping(source = "customer.customerName", target = "customerName")
    SaleDTO saleToSaleDTO(Sale sale);

    Sale saleDTOToSale(SaleDTO saleDTO);

    Sale saleCreateDTOToSale(SaleCreateDTO saleCreateDTO);
}
