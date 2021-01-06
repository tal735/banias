package com.app.service.user;

import com.app.model.booking.Booking;
import com.app.model.user.User;
import com.app.service.booking.BookingService;
import com.app.service.otp.OTPService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public class OTPUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final BookingService bookingService;
    private final OTPService otpService;

    public OTPUserDetailsServiceImpl(UserService userService, BookingService bookingService, OTPService otpService) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.otpService = otpService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String otp = otpService.getIfPresent(username);
        if (StringUtils.isBlank(otp)) {
            throw new UsernameNotFoundException("OTP not found in DB for " + username);
        }

        Set<SimpleGrantedAuthority> authorities = Sets.newHashSet(new SimpleGrantedAuthority("ROLE_OTP"));
        SessionUserDetails u = new SessionUserDetails();
        u.setPassword(otp);
        u.setAuthorities(authorities);

        if (username.contains("@")) {
            String email = username.toLowerCase().trim();
            User user = userService.getByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("Booker email [" + username + "] was not found.");
            }
            authorities.add(new SimpleGrantedAuthority("ROLE_OTP_BOOK"));
            u.setUsername(user.getEmail());
            u.setUserId(user.getId());
        }
        else {
            Booking booking = bookingService.getByReference(username);
            if (booking == null) {
                throw new UsernameNotFoundException("Booker reference [" + username + "] was not found.");
            }
            authorities.add(new SimpleGrantedAuthority("ROLE_OTP_VIEW"));
            u.setUsername(booking.getReference());
            u.setBookingId(booking.getId());
            u.setUserId(booking.getUser().getId());
        }

        return u;
    }
}
