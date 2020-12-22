package com.app.controller.user;

import com.app.model.user.SessionUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    private String countryCode;
    private String phone;
    private boolean verified;

    public UserDTO(SessionUser user) {
        if (user != null) {
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.email = user.getEmail();
            this.countryCode = user.getCountryCode();
            this.phone = user.getPhone();
            this.verified = user.isVerified();

            List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).map(role -> role.replace("ROLE_", "")).collect(Collectors.toList());
            roles.add("ADMIN");
            this.roles = roles;
        }
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
