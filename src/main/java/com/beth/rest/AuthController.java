package com.beth.rest;

import com.beth.auth.JwtAuthenticationResponse;
import com.beth.auth.JwtTokenProvider;
import com.beth.domain.NoncePayload;
import com.beth.domain.RegistrationPayload;
import com.beth.domain.SignPayload;
import com.beth.domain.User;
import com.beth.exception.UnknownAddressException;
import com.beth.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @GetMapping("challenge/{address}")
    public NoncePayload challenge(@PathVariable("address") String address) {
        val user = userService.findByAddress(address);

        if (user == null) {
            throw new UnknownAddressException("Unknown address: " + address);
        }

        return new NoncePayload().setNonce(user.getNonce());
    }

    @PostMapping("auth")
    public JwtAuthenticationResponse auth(@RequestBody SignPayload payload) {
        val authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                payload.getAddress(), payload.getSignature()));

        return new JwtAuthenticationResponse()
                .setToken(tokenProvider.generateToken(authentication))
                .setAddress(payload.getAddress());
    }

    @PostMapping("register")
    public User register(@RequestBody @Valid RegistrationPayload payload) {
        return userService.register(payload);
    }
}
