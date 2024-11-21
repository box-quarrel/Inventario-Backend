package com.hameed.inventario.model.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PurchaseDTO {
    private Long id;

    private String purchaseNumber;

    private String purchaseStatus;

    private Double discount;

    private Double totalAmount;

    private String notes;

    private SupplierDTO supplier;

    private List<POLineDTO> purchaseLines; // change this to purchaseLines

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastUpdateBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdateDate;
}
