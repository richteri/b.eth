package com.beth.rest;

import com.beth.domain.Bet;
import com.beth.domain.BetPayload;
import com.beth.service.BetService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("bets")
@RequiredArgsConstructor
public class BetController {

    private final BetService service;

    @GetMapping("my-bets")
    List<Bet> findMyBets() {
        return service.findAllByCurrentUser();
    }

    @GetMapping
    List<Bet> findAllBets(@RequestParam(name = "q", required = false) String q) {
        if (StringUtils.hasText(q)) {
            return service.findAllByText(q);
        }

        return service.findNewBets();
    }

    @GetMapping("{id}")
    public Bet findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @PostMapping
    Bet placeBet(@RequestBody @Valid BetPayload payload) {
        return service.create(payload);
    }

    @GetMapping("count")
    Map<String, Long> count() {
        return Collections.singletonMap("count", service.count());
    }
}
