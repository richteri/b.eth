package com.beth.auth;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private static final String TOKEN_TYPE = "Bearer";

    private String tokenType = TOKEN_TYPE;
    private String token;
    private String address;
}
