package com.beth.domain;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("users")
@Data
public class User {

    @Id private String id;

    private String address;

    private String nonce;

    private String name;

    private long wins;

    private long losses;

    private long verdicts;

    /** wins - losses + verdicts */
    private long rank;

    private Date registrationDate;
}
