package com.hameed.inventario.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Data
@Builder
public class ReceiveOrderDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double discount; // this means a discount amount over the whole total amount

    @UniqueElements(message = "Duplicate received purchase lines detected")
    @Valid
    private List<ReceivedLineDTO> receivedLines;
}
