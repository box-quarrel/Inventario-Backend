package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.CustomerMapper;
import com.hameed.inventario.model.dto.basic.CustomerDTO;
import com.hameed.inventario.model.entity.Customer;
import com.hameed.inventario.repository.CustomerRepository;
import com.hameed.inventario.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository =  customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer resutlCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDTO(resutlCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            // map fields of dto to customer
            customer.setCustomerName(customerDTO.getCustomerName());
            customer.setEmail(customerDTO.getEmail());
            customer.setPhone(customerDTO.getPhone());
            customer.setAddress(customerDTO.getAddress());

            // save
            Customer resultCustomer = customerRepository.save(customer);

            // return the updated DTO
            return customerMapper.customerToCustomerDTO(resultCustomer);
        } else {
            throw new ResourceNotFoundException("Customer with this Id: " + customerId + " could not be found");
        }
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.findById(customerId).ifPresentOrElse(
                customer -> {
                    // handling the link with other entities before deleting
                    customer.getSales().forEach(sale -> sale.setCustomer(null));
                    customer.getProductReturns().forEach(productReturn -> productReturn.setCustomer(null));

                    customerRepository.delete(customer);
                },
                () -> {
                    throw new ResourceNotFoundException("Customer with this Id: " + customerId + " could not be found");
                }
        );
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = getCustomerEntityById(customerId);
        return customerMapper.customerToCustomerDTO(customer);
    }

    @Override
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        Page<Customer> pageCustomers = customerRepository.findAll(pageable);
        return pageCustomers.map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Customer getCustomerEntityById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer with this Id: " + customerId + " could not be found"));
    }
}
