package com.app.config.security.dao;

import com.app.model.user.User;
import com.app.service.user.SessionUserDetails;
import com.app.service.user.UserService;
import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email.toLowerCase().trim());

        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " Not Found.");
        }

        Set<SimpleGrantedAuthority> authorities = Sets.newHashSet(new SimpleGrantedAuthority("ROLE_USER"));
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

        SessionUserDetails u = new SessionUserDetails();
        u.setUserId(user.getId());
        u.setUsername(user.getEmail());
        u.setPassword(user.getPassword());
        u.setAuthorities(authorities);

        return u;
    }
}
