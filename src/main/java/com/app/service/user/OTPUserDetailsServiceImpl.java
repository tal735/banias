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
        if (username.contains("@")) {
            username = username.toLowerCase().trim();
        }
        String otp = otpService.getIfPresent(username);
        if (StringUtils.isBlank(otp)) {
            throw new UsernameNotFoundException("OTP not found/expired " + username);
        }

        User user;
        if (username.contains("@")) {
            user = userService.getByEmail(username);
        } else {
            Booking booking = bookingService.getByReference(username);
            user = booking.getUser();
        }

        SessionUserDetails u = new SessionUserDetails();
        u.setPassword(otp);
        u.setAuthorities(Sets.newHashSet(new SimpleGrantedAuthority("ROLE_USER")));
        u.setUsername(user.getEmail());
        u.setUserId(user.getId());

        return u;
    }
}
