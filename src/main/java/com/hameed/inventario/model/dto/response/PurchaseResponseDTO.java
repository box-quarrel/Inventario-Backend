package com.hameed.inventario.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hameed.inventario.model.dto.basic.SupplierDTO;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PurchaseResponseDTO {
    private Long id;

    private String purchaseNumber;

    private String purchaseStatus;

    private Double discount;

    private Double totalAmount;

    private String notes;

    private SupplierDTO supplier;

    @UniqueElements
    private List<POLineResponseDTO> purchaseLines; // change this to purchaseLines

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
