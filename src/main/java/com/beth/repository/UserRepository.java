package com.beth.repository;

import com.beth.domain.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{name:'?0'}")
    User findAllByName(String name);

    @Query("{address:'?0'}")
    User findAllByAddress(String address);

    @Query("{ 'rank' : { $ne: null } }")
    List<User> findTop100ByRankNotNullOrderByRankDesc();

    long count();
}
