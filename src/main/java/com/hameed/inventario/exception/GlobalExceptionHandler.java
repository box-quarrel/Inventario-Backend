package com.hameed.inventario.exception;

import com.hameed.inventario.model.dto.response.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
//
//        // Prepare Exception Response
//        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
//                .type(URI.create("https://example.com/problems/internal-server-error"))
//                .title("Internal Server Error")
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .detail(ex.getMessage())
//                .instance(URI.create("/error"))
//                .timestamp(ZonedDateTime.now().toString())
//                .build();
//
//        // Logging
//        String requestId = MDC.get("requestId");
//        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);
//
//        // Return Http Response
//        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .type(URI.create("https://example.com/problems/resource-not-found"))
                .title("Resource Not Found")
                .status(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .timestamp(ZonedDateTime.now().toString())
                .build();

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
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .type(URI.create("https://example.com/problems/duplicate-code"))
                .title("Duplicate Code")
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
                .timestamp(ZonedDateTime.now().toString())
                .build();

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RecordCannotBeModifiedException.class)
    public ResponseEntity<ErrorResponseDTO> handleRecordCannotBeModifiedException(RecordCannotBeModifiedException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .type(URI.create("https://example.com/problems/record-cannot-be-modified"))
                .title("Record Cannot be Modified")
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
                .timestamp(ZonedDateTime.now().toString())
                .build();

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(InventoryOutOfStockException.class)
    public ResponseEntity<ErrorResponseDTO> handleInventoryOutOfStockException(InventoryOutOfStockException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .type(URI.create("https://example.com/problems/inventory-out-of-stock"))
                .title("Inventory out of Stock")
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
                .timestamp(ZonedDateTime.now().toString())
                .build();

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }



    /*
        Bean-Validation Exceptions Handling
     */

    // Thrown by Hibernate when a database constraint is violated (e.g. unique constraints, foreign key violations)
    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleHibernateConstraintViolationException(org.hibernate.exception.ConstraintViolationException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .type(URI.create("https://example.com/validation-error"))
                .title("Validation Error")
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
//        .instance(URI.create("/current-endpoint")) // Uncomment and use the actual endpoint causing the error
                .timestamp(ZonedDateTime.now().toString())
                .build();

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
    }

    // Thrown by the Bean Validation API during persistence
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(jakarta.validation.ConstraintViolationException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .type(URI.create("https://example.com/validation-error"))
                .title("Validation Error")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
//        .instance(URI.create("/current-endpoint")) // Uncomment and use the actual endpoint causing the error
                .timestamp(ZonedDateTime.now().toString())
                .build();

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    // Thrown by spring framework when an attempt to execute an SQL statement fails to map the given data, typically but no limited to an insert or update data results in violation of an integrity constraint.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .type(URI.create("https://example.com/database-error"))
                .title("Database Constraint Violation")
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
//        .instance(URI.create("/current-endpoint")) // Uncomment and use the actual endpoint causing the error
                .timestamp(ZonedDateTime.now().toString())
                .build();

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
    }

    @ExceptionHandler(InvalidReturnQuantityException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidReturnQuantityException(InvalidReturnQuantityException ex) {

        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .type(URI.create("https://example.com/return-quantiy-error"))
                .title("Invalid Return Quantity")
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
//        .instance(URI.create("/current-endpoint")) // Uncomment and use the actual endpoint causing the error
                .timestamp(ZonedDateTime.now().toString())
                .build();

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // Prepare Error message
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();
            } catch (ClassCastException e) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            errorMessage.append(String.format("%s: %s | ", fieldName, message));
        });
        // Prepare Exception Response
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .type(URI.create("https://example.com/return-quantiy-error"))
                .title("Bean Validation Exception")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(errorMessage.toString())
//        .instance(URI.create("/current-endpoint")) // Uncomment and use the actual endpoint causing the error
                .timestamp(ZonedDateTime.now().toString())
                .build();

        // Logging
        String requestId = MDC.get("requestId");
        LOGGER.error("Request ID: {}, Exception: {}", requestId, errorResponseDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

}
