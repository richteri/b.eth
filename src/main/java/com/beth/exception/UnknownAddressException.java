package com.beth.exception;

public class UnknownAddressException extends RuntimeException {

    public UnknownAddressException(String message) {
        super(message);
    }
}
