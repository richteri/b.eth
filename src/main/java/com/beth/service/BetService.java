package com.beth.service;

import com.beth.auth.JwtUserDetailsService;
import com.beth.domain.Bet;
import com.beth.domain.BetPayload;
import com.beth.domain.UserRef;
import com.beth.exception.NotFoundException;
import com.beth.repository.BetRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BetService {

    private final JwtUserDetailsService authService;
    private final BetRepository repository;

    public List<Bet> findAllByCurrentUser() {
        val currentUserId = authService.currentUser().getId();
        val sort = Sort.by(Sort.Direction.DESC, "deadline");

        return repository.findAllByUser(currentUserId, sort);
    }

    public List<Bet> findNewBets() {
        val sort = Sort.by(Sort.Direction.DESC, "betDate");
        val page = PageRequest.of(0, 20, sort);

        return repository.findAllBy(page);
    }

    public List<Bet> findAllByText(String text) {
        val criteria = TextCriteria.forDefaultLanguage().matching(text);
        val page = PageRequest.of(0, 20);

        return repository.findAllBy(criteria, page);
    }

    public long count() {
        return repository.count();
    }

    @Transactional
    public Bet create(BetPayload payload) {
        val user = authService.currentUser();

        return repository.save(
                new Bet()
                        .setDescription(payload.getDescription())
                        .setCallDeadline(payload.getCallDeadline())
                        .setDecisionDeadline(payload.getDecisionDeadline())
                        .setBetDate(new Date())
                        .setBetter(
                                new UserRef()
                                        .setAddress(user.getAddress())
                                        .setName(user.getName())));
    }

    public Bet findById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Bet not found"));
    }
}
