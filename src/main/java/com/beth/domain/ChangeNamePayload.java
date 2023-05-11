package com.beth.domain;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChangeNamePayload {

    @NotBlank private String name;
}
