package com.hameed.inventario.controller;

import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.model.dto.basic.CustomerDTO;
import com.hameed.inventario.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario/api/v1/customers")
public class CustomerController {

    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<CustomerDTO>> getAllCustomers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<CustomerDTO> customerDTOPage = customerService.getAllCustomers(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Customers Retrieved Successfully", customerDTOPage)); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CustomerDTO>> getCustomerById(@PathVariable Long id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        return ResponseEntity.ok(new ResponseDTO<>(200, "Customer Retrieved Successfully", customerDTO)); // 200 OK
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO<CustomerDTO>> addCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO resultCustomerDTO = customerService.addCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Customer Created Successfully", resultCustomerDTO));  // 201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<CustomerDTO>> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO resultCustomerDTO = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Customer Updated Successfully", resultCustomerDTO));  // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<CustomerDTO>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseDTO<>(204, "Customer Deleted Successfully"));  // 204 NO_CONTENT
    }


}
