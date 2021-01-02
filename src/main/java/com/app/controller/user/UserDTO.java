package com.app.controller.user;

import com.app.service.user.SessionUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private List<String> roles;

    public UserDTO(SessionUserDetails user) {
        if (user != null) {
            this.roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(role -> role.replace("ROLE_", ""))
                    .collect(Collectors.toList());
        }
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
