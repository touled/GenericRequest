package com.example.demo.error;

public class QuoteServiceException extends RuntimeException {
    public QuoteServiceException(String message) {
        super(message);
    }

    public QuoteServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
