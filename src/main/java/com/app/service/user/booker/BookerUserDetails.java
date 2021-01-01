package com.app.service.user.booker;

import com.google.common.collect.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class BookerUserDetails implements UserDetails {

    private final Long bookingId;
    private final String reference;
    private final String otp;
    private final List<SimpleGrantedAuthority> authorities = Lists.newArrayList(new SimpleGrantedAuthority("ROLE_OTP"));

    public BookerUserDetails(Long bookingId, String reference, String otp) {
        this.bookingId = bookingId;
        this.reference = reference;
        this.otp = otp;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.otp;
    }

    @Override
    public String getUsername() {
        return this.reference;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getBookingId() {
        return bookingId;
    }
}
