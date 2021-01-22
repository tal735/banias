package com.app.config.security.dao;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class DaoAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public DaoAuthenticationToken(String username, String password) {
        super(username, password);
    }

}
