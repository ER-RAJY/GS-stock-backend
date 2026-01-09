package com.gsstock.backend.exception;

public class StockInsufficientException extends RuntimeException {

    public StockInsufficientException() {
        super("Stock insuffisant");
    }
}
