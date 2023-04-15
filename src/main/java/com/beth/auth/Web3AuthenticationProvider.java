package com.beth.auth;

import com.beth.exception.Web3AuthenticationException;
import com.beth.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SignatureException;

@Component
@RequiredArgsConstructor
public class Web3AuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        val user = userService.findByAddress(authentication.getName());
        val signature = authentication.getCredentials().toString();
        if (verifySignature(user.getAddress(), user.getNonce(), signature)) {
            return new Web3Authentication(user.getAddress(), signature);
        } else {
            throw new Web3AuthenticationException("Invalid signature");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Web3Authentication.class.isAssignableFrom(authentication);
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
