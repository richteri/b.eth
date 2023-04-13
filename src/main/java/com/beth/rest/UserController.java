package com.beth.rest;

import com.beth.domain.User;
import com.beth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;

    @GetMapping
    List<User> list() {
        return repository.findAll();
    }
}
