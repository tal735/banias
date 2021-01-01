package com.app.service.user;

import com.app.model.user.User;
import com.google.common.collect.Lists;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

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

        List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
