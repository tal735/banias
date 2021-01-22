package com.app.config.security.otp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class OTPAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public OTPAuthenticationToken(String username, String password) {
        super(username, password);
    }

}
