package com.beth.domain;

import lombok.Data;

@Data
public class SignPayload {
    private String address;
    private String signature;
}
