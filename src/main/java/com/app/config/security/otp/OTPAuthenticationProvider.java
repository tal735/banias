package com.app.config.security.otp;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

public class OTPAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        return (OTPAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
