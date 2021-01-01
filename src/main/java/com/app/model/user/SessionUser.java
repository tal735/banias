package com.app.model.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public class SessionUser extends org.springframework.security.core.userdetails.User {

    private Long userId;
    private String email;

    public SessionUser(User user, Collection<SimpleGrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        setUserId(user.getId());
        setEmail(user.getEmail());
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
