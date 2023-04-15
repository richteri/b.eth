package com.beth.rest;

import com.beth.auth.Web3Authentication;
import com.beth.domain.SignPayload;
import com.beth.exception.UnknownAddressException;
import com.beth.service.UserService;

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
    public String challenge(@PathVariable("address") String address) {
        val user = userService.findByAddress(address);

        if (user == null) {
            throw new UnknownAddressException("Unknown address: " + address);
        }

        return user.getNonce();
    }

    @PostMapping("auth")
    public Authentication auth(@RequestBody SignPayload payload) {
        return authenticationManager.authenticate(
                new Web3Authentication(payload.getAddress(), payload.getSignature()));
    }
}
