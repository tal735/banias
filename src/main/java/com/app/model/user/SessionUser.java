package com.app.model.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SessionUser extends org.springframework.security.core.userdetails.User {

    private Long userId;
    private String email;

    public SessionUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
