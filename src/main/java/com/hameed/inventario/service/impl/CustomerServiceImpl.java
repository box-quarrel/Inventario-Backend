package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.CustomerMapper;
import com.hameed.inventario.model.dto.update.CustomerDTO;
import com.hameed.inventario.model.entity.Customer;
import com.hameed.inventario.repository.CustomerRepository;
import com.hameed.inventario.repository.ProductReturnRepository;
import com.hameed.inventario.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ProductReturnRepository productReturnRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ProductReturnRepository productReturnRepository) {
        this.customerRepository =  customerRepository;
        this.productReturnRepository = productReturnRepository;
    }


    @Override
    public void addCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO);
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        Long customerId = customerDTO.getId();
        customerRepository.findById(customerId).ifPresentOrElse(
                customer -> {
                    // map fields of dto to customer
                    customer.setCustomerName(customerDTO.getCustomerName());
                    customer.setEmail(customerDTO.getEmail());
                    customer.setPhone(customerDTO.getPhone());
                    customer.setAddress(customerDTO.getAddress());

                    // save
                    customerRepository.save(customer);
                },
                () -> {
                    throw new ResourceNotFoundException("Customer with this Id: " + customerId + " could not be found");
                }
        );
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
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
    }

    @Override
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        Page<Customer> pageCustomers = customerRepository.findAll(pageable);
        return pageCustomers.map(CustomerMapper.INSTANCE::customerToCustomerDTO);
    }

    @Override
    public Customer getCustomerEntityById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer with this Id: " + customerId + " could not be found"));
    }
}
