package com.hameed.inventario.exception;

public class RecordCannotBeModifiedException extends RuntimeException {
    public RecordCannotBeModifiedException(String message) {
        super(message);
    }
}
