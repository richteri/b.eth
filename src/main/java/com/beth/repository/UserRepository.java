package com.beth.repository;

import com.beth.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{name:'?0'}")
    User findByName(String name);

    @Query("{address:'?0'}")
    User findByAddress(String address);

    long count();
}
