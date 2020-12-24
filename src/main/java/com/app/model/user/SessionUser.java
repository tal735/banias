package com.app.model.user;

import com.google.common.collect.Lists;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SessionUser extends org.springframework.security.core.userdetails.User {

    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String countryCode;
    private String phone;
    private boolean verified;

    public SessionUser(User user) {
        super(user.getEmail(), user.getPassword(), Lists.newArrayList(
                        new SimpleGrantedAuthority("ROLE_USER"),
                        new SimpleGrantedAuthority("ROLE_ADMIN")));
        setUserId(user.getId());
        setEmail(user.getEmail());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setCountryCode(user.getCountryCode());
        setPhone(user.getPhone());
        setVerified(user.isVerified());
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
