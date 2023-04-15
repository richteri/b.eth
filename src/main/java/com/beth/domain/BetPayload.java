package com.beth.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.Date;

@Data
public class BetPayload {

    @NotNull private Date callDeadline;

    /** Earliest time when the winner can be decided */
    @NotNull private Date decisionDeadline;

    @NotBlank private String description;
}
