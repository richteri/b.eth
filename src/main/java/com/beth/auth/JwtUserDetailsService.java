package com.beth.auth;

import com.beth.domain.User;
import com.beth.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public User currentUser() {
        val address = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.findByAddress(address.toString());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userService.findByAddress(username);

        return new JwtUserDetails().setUsername(username).setPassword("");
    }
}
