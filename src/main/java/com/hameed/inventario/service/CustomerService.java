package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.CustomerDTO;
import org.springframework.data.domain.Page;


public interface CustomerService {
    // add a new customer
    public void addCustomer(CustomerDTO customerDTO);

    // Update an existing customer
    public void updateCustomer(String customerId, CustomerDTO customerDTO);

    // Remove a Customer by its ID (handle cases where the Customer is linked to other entities)
    public void deleteCustomer(String CustomerId);

    // Fetch a Customer by its ID
    public CustomerDTO getCustomerById(String CustomerId);

    // List all categories
    public Page<CustomerDTO> getAllCustomers();
}
