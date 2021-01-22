package com.app.config.security.dao;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

public class DAOAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        return (DaoAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
