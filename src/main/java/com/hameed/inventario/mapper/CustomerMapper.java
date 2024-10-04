package com.hameed.inventario.mapper;

import com.hameed.inventario.model.dto.update.CustomerDTO;
import com.hameed.inventario.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    // for a singleton mapper
    CustomerMapper  INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
