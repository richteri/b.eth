package com.beth.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("users")
@Data
public class User {

    @Id
    private String id;

    private String address;

    private String name;

    private int refills;

    private BigDecimal balance;

}
