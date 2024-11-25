package com.hameed.inventario.exception;

import com.hameed.inventario.model.dto.response.ErrorResponseDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /*
        General Exceptions Handling
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/problems/internal-server-error"));
        errorResponseDTO.setTitle("Internal Server Error");
        errorResponseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponseDTO.setDetail(ex.getMessage());
        errorResponseDTO.setInstance(URI.create("/error"));
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        // Return Http Response
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/problems/resource-not-found"));
        errorResponseDTO.setTitle("Resource Not Found");
        errorResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponseDTO.setDetail(ex.getMessage());
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    /*
        Service-Validation Exceptions Handling
     */

    @ExceptionHandler(DuplicateCodeException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateCodeException(DuplicateCodeException ex) {

        // Prepare Exception Response
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

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/problems/record-cannot-be-modified"));
        errorResponseDTO.setTitle("Record Cannot be Modified");
        errorResponseDTO.setStatus(HttpStatus.CONFLICT.value());
        errorResponseDTO.setDetail(ex.getMessage());
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(InventoryOutOfStockException.class)
    public ResponseEntity<ErrorResponseDTO> handleInventoryOutOfStockException(InventoryOutOfStockException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/problems/inventory-out-of-stock"));
        errorResponseDTO.setTitle("Inventory out of Stock");
        errorResponseDTO.setStatus(HttpStatus.CONFLICT.value());
        errorResponseDTO.setDetail(ex.getMessage());
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }



    /*
        Bean-Validation Exceptions Handling
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/validation-error"));
        errorResponseDTO.setTitle("Validation Error");
        errorResponseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseDTO.setDetail(ex.getMessage());
//        error.setInstance(URI.create("/current-endpoint")); // Use the actual endpoint causing the error
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setType(URI.create("https://example.com/database-error"));
        errorResponseDTO.setTitle("Database Constraint Violation");
        errorResponseDTO.setStatus(HttpStatus.CONFLICT.value());
        errorResponseDTO.setDetail(ex.getMessage());
//        error.setInstance(URI.create("/current-endpoint")); // Use the actual endpoint causing the error
        errorResponseDTO.setTimestamp(ZonedDateTime.now().toString());

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
    }

}
