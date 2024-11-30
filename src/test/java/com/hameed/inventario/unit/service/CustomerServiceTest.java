package com.hameed.inventario.unit.service;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.CustomerMapper;
import com.hameed.inventario.model.dto.basic.CustomerDTO;
import com.hameed.inventario.model.entity.Customer;
import com.hameed.inventario.repository.CustomerRepository;
import com.hameed.inventario.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("Customer Added Successfully")
    public void testAddCustomer_shouldReturnAddedCustomerAsCustomerDTO() {
        //  --- Arrange ---
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();

        Customer mockMappedCustomer = Customer.builder()
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();

        Customer mockResultCustomer = Customer.builder()
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();
        mockResultCustomer.setId(1L);  // Set the ID explicitly

        CustomerDTO expectedCustomerDTO = CustomerDTO.builder()
                .id(1L)
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();



        // Define behavior of the mocks
        Mockito.when(customerMapper.customerDTOToCustomer(customerDTO)).thenReturn(mockMappedCustomer);
        Mockito.when(customerRepository.save(mockMappedCustomer)).thenReturn(mockResultCustomer);
        Mockito.when(customerMapper.customerToCustomerDTO(mockResultCustomer)).thenReturn(expectedCustomerDTO);

        // --- Act ---
        CustomerDTO resultCustomerDTO = customerService.addCustomer(customerDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultCustomerDTO, "result customer DTO is null"),
                () -> assertNotNull(resultCustomerDTO.getId(), "result customer DTO does not include an ID"),
                () -> assertEquals(expectedCustomerDTO, resultCustomerDTO, "Expected customer DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("Customer updated successfully")
    public void testUpdateCustomer_shouldReturnCustomerAsCustomerDTO() {
        // --- Arrange ---
        Long customerId = 1L;
        // CustomerDTO without an id during creation
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();

// Creating Customer entities
        Customer existingCustomer = Customer.builder()
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();
        existingCustomer.setId(customerId);

        Customer updatedCustomer = Customer.builder()
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();
        updatedCustomer.setId(customerId);

// CustomerDTO with id for expected result
        CustomerDTO expectedCustomerDTO = CustomerDTO.builder()
                .id(customerId)
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();



        // Define behavior of the mocks
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        Mockito.when(customerRepository.save(existingCustomer)).thenReturn(updatedCustomer);
        Mockito.when(customerMapper.customerToCustomerDTO(updatedCustomer)).thenReturn(expectedCustomerDTO);

        // --- Act ---
        CustomerDTO resultCustomerDTO = customerService.updateCustomer(customerId, customerDTO);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultCustomerDTO, "result updated customer DTO is null"),
                () -> assertNotNull(resultCustomerDTO.getId(), "result updated customer DTO does not include an ID"),
                () -> assertEquals(expectedCustomerDTO, resultCustomerDTO, "Expected customer DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("Customer successfully not updated because id was not found")
    public void testUpdateCustomer_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long customerId = 1L;
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();


        // Define behavior of the mocks
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());


        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(customerId, customerDTO));
    }

    @Test
    @DisplayName("Customer deleted successfully")
    public void testDeleteCustomer_shouldCallDeleteCustomerOnce() {
        // --- Arrange ---
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .sales(new HashSet<>())
                .build();
        customer.setId(customerId);  // Set the id after using the builder

        // Define behavior of the mocks
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // --- Act ---
        customerService.deleteCustomer(customerId);

        // --- Assert ---
        Mockito.verify(customerRepository, Mockito.times(1)).delete(customer);
    }

    @Test
    @DisplayName("Customer successfully not deleted because customer was not found")
    public void testDeleteCustomer_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long customerId = 1L;


        // Define behavior of the mocks
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(customerId), "Expected ResourceNotFoundException to be thrown");
    }

    @Test
    @DisplayName("Customer retrieved successfully")
    public void testGetCustomer_shouldReturnCustomerAsCustomerDTO() {
        //  --- Arrange ---
        Long customerId = 1L;

// Using builder pattern to create Customer
        Customer mockResultCustomer = Customer.builder()
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();
        mockResultCustomer.setId(customerId);  // Set the id after using the builder

// Using builder pattern to create CustomerDTO
        CustomerDTO expectedCustomerDTO = CustomerDTO.builder()
                .id(customerId)
                .customerName("Dan Bran")
                .email("danbran@example.com 1")
                .phone("123456")
                .address("123 Main St, Manhaten")
                .build();


        // Define behavior of the mocks
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockResultCustomer));
        Mockito.when(customerMapper.customerToCustomerDTO(mockResultCustomer)).thenReturn(expectedCustomerDTO);

        // --- Act ---
        CustomerDTO resultCustomerDTO = customerService.getCustomerById(customerId);

        // --- Assert ---
        assertAll(
                () -> assertNotNull(resultCustomerDTO, "result customer DTO is null"),
                () -> assertNotNull(resultCustomerDTO.getId(), "result customer DTO does not include an ID"),
                () -> assertEquals(expectedCustomerDTO, resultCustomerDTO, "Expected customer DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
        );
    }

    @Test
    @DisplayName("Customer successfully not retrieved because customer was not found")
    public void testGetCustomer_shouldThrowResourceNotFoundException() {
        // --- Arrange ---
        Long customerId = 1L;


        // Define behavior of the mocks
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(customerId), "Expected ResourceNotFoundException to be thrown");
    }
}
