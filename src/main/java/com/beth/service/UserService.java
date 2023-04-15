package com.beth.service;

import com.beth.domain.User;
import com.beth.domain.UserPayload;
import com.beth.exception.NotFoundException;
import com.beth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findTop100() {
        return repository.findTop100ByRankNotNullOrderByRankDesc();
    }

    public long count() {
        return repository.count();
    }

    @Transactional
    public User create(UserPayload payload) {
        return repository.save(
                new User()
                        .setName(payload.getName())
                        .setAddress(payload.getAddress())
                        .setNonce(UUID.randomUUID().toString()));
    }

    public User findByAddress(String address) {
        return repository.findAllByAddress(address);
    }

    public User findById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
