package com.hameed.inventario.model.dto.update;

import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;
    
    private String customerName;

    
    private String email;

    
    private String phone;

    
    private String address;
}
