package com.hameed.inventario.model.dto.update;

import lombok.Data;

@Data
public class SupplierDTO {
    private Long id;
    private String supplierName;
    private String contactName;
    private String contactPhone;
    private String email;
    private String address;
}
