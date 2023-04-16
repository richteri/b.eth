package com.beth.repository;

import com.beth.domain.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{name:'?0'}")
    Optional<User> findByName(String name);

    @Query("{address:'?0'}")
    Optional<User> findByAddress(String address);

    @Query("{ 'rank' : { $ne: null } }")
    List<User> findTop100ByRankNotNullOrderByRankDesc();

    long count();
}
