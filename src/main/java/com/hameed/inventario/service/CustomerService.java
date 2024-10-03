package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.CustomerDTO;
import com.hameed.inventario.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerService {
    // add a new customer
    public void addCustomer(CustomerDTO customerDTO);

    // Update an existing customer
    public void updateCustomer(Long customerId, CustomerDTO customerDTO);

    // Remove a Customer by its ID
    // handle cases where the Customer is linked to other entities (sales and product returns)
    public void deleteCustomer(Long customerId);

    // Fetch a Customer by its ID
    public CustomerDTO getCustomerById(Long customerId);

    // List all categories
    public Page<CustomerDTO> getAllCustomers(Pageable pageable);

    // Gets the customer entity by ID
    public Customer getCustomerEntityById(Long customerId);
}
