package com.app.service.user;

import com.app.model.user.SessionUser;
import com.app.model.user.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " Not Found.");
        }
        List<GrantedAuthority> authorities = Lists.newArrayList();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        SessionUser sessionUser = new SessionUser(user.getEmail(), user.getPassword(), authorities);
        sessionUser.setUserId(user.getId());
        sessionUser.setEmail(user.getEmail());
        return sessionUser;
    }
}
