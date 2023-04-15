package com.beth.repository;

import com.beth.domain.Bet;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BetRepository extends MongoRepository<Bet, String> {

    @Query("{ $or: [ { 'better.id': ?0 },  { 'judge.id': ?0 }, { 'caller.id': ?0 } ] }")
    List<Bet> findAllByUser(String userId, Sort sort);

    List<Bet> findAllBy(Pageable pageable);

    List<Bet> findAllBy(TextCriteria criteria, Pageable pageable);

    long count();
}
