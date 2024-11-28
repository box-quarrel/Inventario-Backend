package com.hameed.inventario.model.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
public class PaginatedResponseDTO<T> {

    private Integer status;
    private String message;
    private List<T> dataPage;
    // page-related attributes
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;

    public PaginatedResponseDTO(Integer status, String message, Page<T> page) {
        this.status = status;
        this.message = message;
        this.dataPage = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
    }
}
