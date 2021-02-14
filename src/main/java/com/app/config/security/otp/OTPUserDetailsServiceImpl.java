package com.app.config.security.otp;

import com.app.config.security.AuthoritiesConstants;
import com.app.model.booking.Booking;
import com.app.model.user.User;
import com.app.service.booking.BookingService;
import com.app.service.otp.OTPService;
import com.app.service.user.SessionUserDetails;
import com.app.service.user.UserService;
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
        User user = null;
        if (username.contains("@")) {
            user = userService.getByEmail(username.toLowerCase().trim());
        } else {
            Booking booking = bookingService.getByReference(username);
            if (booking != null) {
                user = booking.getUser();
            }
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found for " + username);
        }

        String otp = otpService.getIfPresent(user.getEmail());
        if (StringUtils.isBlank(otp)) {
            throw new UsernameNotFoundException("OTP not found/expired " + username);
        }

        SessionUserDetails u = new SessionUserDetails();
        u.setPassword(otp);
        u.setAuthorities(Sets.newHashSet(new SimpleGrantedAuthority(AuthoritiesConstants.USER)));
        u.setUsername(user.getEmail());
        u.setUserId(user.getId());

        return u;
    }
}
