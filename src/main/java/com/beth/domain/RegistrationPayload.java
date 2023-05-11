package com.beth.domain;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RegistrationPayload {

    @NotBlank private String name;

    @NotBlank private String address;
}
