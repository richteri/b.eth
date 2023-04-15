package com.beth.domain;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("bets")
@Data
public class Bet {

    @Id private String id;

    private Date callDeadline;

    /** Earliest time when the winner can be decided */
    private Date decisionDeadline;

    private Date decisionDate;

    private String description;

    private UserRef better;

    private Date betDate;

    private UserRef caller;

    private Date callDate;

    private UserRef judge;

    private UserRef judgeDate;

    /** 1 - bet won, 2 - call won, 3 - draw */
    private int result;
}
