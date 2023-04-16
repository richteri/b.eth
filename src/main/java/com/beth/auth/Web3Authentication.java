package com.beth.auth;

import lombok.EqualsAndHashCode;

import org.springframework.security.authentication.AbstractAuthenticationToken;

@EqualsAndHashCode(callSuper = true)
public class Web3Authentication extends AbstractAuthenticationToken {

    private final String address;
    private final String signature;

    public Web3Authentication(String address, String signature) {
        super(null);
        this.address = address;
        this.signature = signature;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return signature;
    }

    @Override
    public Object getPrincipal() {
        return address;
    }
}
