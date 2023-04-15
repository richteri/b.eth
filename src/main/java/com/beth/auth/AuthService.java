package com.beth.auth;

import com.beth.domain.User;
import com.beth.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public User currentUser() {
        val address = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.findByAddress(address.toString());
    }
}
