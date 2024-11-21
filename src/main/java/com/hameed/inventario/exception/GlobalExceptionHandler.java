package com.hameed.inventario.exception;

import com.hameed.inventario.model.dto.response.ErrorResponseDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
        General Exceptions Handling
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/problems/internal-server-error"));
        errorResponseDTO.setTitle("Internal Server Error");
        errorResponseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponseDTO.setDetail("An unexpected error occurred.");
        errorResponseDTO.setInstance(URI.create("/error"));
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/problems/resource-not-found"));
        errorResponseDTO.setTitle("Resource Not Found");
        errorResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponseDTO.setDetail(ex.getMessage());
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    /*
        Service-Validation Exceptions Handling
     */

    @ExceptionHandler(DuplicateCodeException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateCodeException(DuplicateCodeException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/problems/duplicate-code"));
        errorResponseDTO.setTitle("Duplicate Code");
        errorResponseDTO.setStatus(HttpStatus.CONFLICT.value());
        errorResponseDTO.setDetail(ex.getMessage());
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RecordCannotBeModifiedException.class)
    public ResponseEntity<ErrorResponseDTO> handleRecordCannotBeModifiedException(RecordCannotBeModifiedException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/problems/record-cannot-be-modified"));
        errorResponseDTO.setTitle("Record Cannot be Modified");
        errorResponseDTO.setStatus(HttpStatus.CONFLICT.value());
        errorResponseDTO.setDetail(ex.getMessage());
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(InventoryOutOfStockException.class)
    public ResponseEntity<ErrorResponseDTO> handleInventoryOutOfStockException(InventoryOutOfStockException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/problems/inventory-out-of-stock"));
        errorResponseDTO.setTitle("Inventory out of Stock");
        errorResponseDTO.setStatus(HttpStatus.CONFLICT.value());
        errorResponseDTO.setDetail(ex.getMessage());
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }



    /*
        Bean-Validation Exceptions Handling
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setType(URI.create("https://example.com/validation-error"));
        error.setTitle("Validation Error");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDetail(ex.getMessage());
//        error.setInstance(URI.create("/current-endpoint")); // Use the actual endpoint causing the error
        error.setTimestamp(ZonedDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setType(URI.create("https://example.com/database-error"));
        error.setTitle("Database Constraint Violation");
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setDetail(ex.getMessage());
//        error.setInstance(URI.create("/current-endpoint")); // Use the actual endpoint causing the error
        error.setTimestamp(ZonedDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}
