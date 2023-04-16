package com.beth.rest;

import com.beth.auth.Web3Authentication;
import com.beth.domain.NoncePayload;
import com.beth.domain.SignPayload;
import com.beth.domain.User;
import com.beth.domain.UserPayload;
import com.beth.exception.UnknownAddressException;
import com.beth.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("challenge/{address}")
    public NoncePayload challenge(@PathVariable("address") String address) {
        val user = userService.findByAddress(address);

        if (user == null) {
            throw new UnknownAddressException("Unknown address: " + address);
        }

        return new NoncePayload().setNonce(user.getNonce());
    }

    @PostMapping("auth")
    public Authentication auth(@RequestBody SignPayload payload) {
        return authenticationManager.authenticate(
                new Web3Authentication(payload.getAddress(), payload.getSignature()));
    }

    @PostMapping("register")
    public User register(@RequestBody @Valid UserPayload payload) {
        return userService.create(payload);
    }
}
