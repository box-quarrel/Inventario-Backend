package com.hameed.inventario.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hameed.inventario.model.dto.basic.CustomerDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class SaleResponseDTO {
    private Long id;
    private String salesNumber;
    private Double totalAmount;
    private Double netAmount;
    private String discountType;
    private Double discountValue;
    private CustomerDTO customer;
    private Set<SaleItemResponseDTO> saleItems;

//    @JsonInclude(JsonInclude.Include.NON_NULL) // if no returned products this will be excluded from the dto
//    private List<ProductReturnResponseDTO> productReturns;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastUpdateBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdateDate;
}