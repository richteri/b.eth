package com.beth.auth;

import com.beth.exception.Web3AuthenticationException;
import com.beth.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SignatureException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Web3AuthenticationProvider implements AuthenticationProvider {

    private static final String NONCE_PREFIX = "Nonce: ";

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        val user = userService.findByAddress(authentication.getName());
        val nonce = NONCE_PREFIX + user.getNonce();
        val signature = authentication.getCredentials().toString();

        // generate new nonce
        userService.update(user.setNonce(UUID.randomUUID().toString()));

        if (verifySignature(user.getAddress(), nonce, signature)) {
            return new UsernamePasswordAuthenticationToken(user.getAddress(), "");
        } else {
            throw new Web3AuthenticationException("Invalid signature");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean verifySignature(String address, String nonce, String signature) {
        val r = signature.substring(0, 66);
        val s = "0x" + signature.substring(66, 130);
        val v = "0x" + signature.substring(130, 132);

        val data =
                new Sign.SignatureData(
                        Numeric.hexStringToByteArray(v),
                        Numeric.hexStringToByteArray(r),
                        Numeric.hexStringToByteArray(s));

        try {
            BigInteger key = Sign.signedPrefixedMessageToKey(nonce.getBytes(), data);

            return ("0x" + Keys.getAddress(key).toLowerCase()).equals(address.toLowerCase());
        } catch (SignatureException e) {
            return false;
        }
    }
}
