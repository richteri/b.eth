package com.beth.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class Web3AuthenticationException extends BadCredentialsException {

    public Web3AuthenticationException(String message) {
        super(message);
    }
}
