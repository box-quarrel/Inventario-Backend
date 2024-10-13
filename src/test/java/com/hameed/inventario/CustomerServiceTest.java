package com.hameed.inventario;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.CustomerMapper;
import com.hameed.inventario.model.dto.update.CustomerDTO;
import com.hameed.inventario.model.entity.Customer;
import com.hameed.inventario.repository.CustomerRepository;
import com.hameed.inventario.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
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
        CustomerDTO customerDTO = createCustomerDTO("Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten");
        Customer mockMappedCustomer = createCustomer("Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten", null);
        Customer mockResultCustomer = createCustomer("Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten", 1L);
        CustomerDTO expectedCustomerDTO = createCustomerDTO(1L, "Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten");

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
        CustomerDTO customerDTO = createCustomerDTO(1L, "Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten");
        Customer existingCustomer = createCustomer("Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten", 1L);
        Customer updatedCustomer = createCustomer("Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten", 1L);
        CustomerDTO expectedCustomerDTO = createCustomerDTO(1L, "Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten");

        // Define behavior of the mocks
        Mockito.when(customerRepository.findById(customerDTO.getId())).thenReturn(Optional.of(existingCustomer));
        Mockito.when(customerRepository.save(existingCustomer)).thenReturn(updatedCustomer);
        Mockito.when(customerMapper.customerToCustomerDTO(updatedCustomer)).thenReturn(expectedCustomerDTO);

        // --- Act ---
        CustomerDTO resultCustomerDTO = customerService.updateCustomer(customerDTO);

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
        CustomerDTO customerDTO = createCustomerDTO(1L, "Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten");

        // Define behavior of the mocks
        Mockito.when(customerRepository.findById(customerDTO.getId())).thenReturn(Optional.empty());


        // --- Act and Assert ---
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(customerDTO));
    }

    @Test
    @DisplayName("Customer deleted successfully")
    public void testDeleteCustomer_shouldCallDeleteCustomerOnce() {
        // --- Arrange ---
        Long customerId = 1L;
        Customer customer = createCustomer("Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten", 1L);

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
        Customer mockResultCustomer = createCustomer("Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten", 1L);
        CustomerDTO expectedCustomerDTO = createCustomerDTO(1L, "Dan Bran", "danbran@example.com 1", "123456", "123 Main St, Manhaten");

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



    // some utility builder methods
    private CustomerDTO createCustomerDTO(String name, String email, String phone, String address) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerName(name);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setAddress(address);
        return dto;
    }

    private Customer createCustomer(String name, String email, String phone, String address, Long id) {
        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setSales(new HashSet<>());
        customer.setProductReturns(new ArrayList<>());
        if (id != null) {
            customer.setId(id);
        }
        return customer;
    }

    private CustomerDTO createCustomerDTO(Long id, String name, String email, String phone, String address) {
        CustomerDTO dto = createCustomerDTO(name, email, phone, address);
        dto.setId(id);
        return dto;
    }
}
