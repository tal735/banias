package com.app.service.user.booker;

import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import com.app.service.otp.OTPService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class BookerUserDetailsServiceImpl implements UserDetailsService {

    private final BookingService bookingService;
    private final OTPService otpService;

    public BookerUserDetailsServiceImpl(BookingService bookingService, OTPService otpService) {
        this.bookingService = bookingService;
        this.otpService = otpService;
    }

    @Override
    public UserDetails loadUserByUsername(String reference) throws UsernameNotFoundException {
        Booking booking = bookingService.getByReference(reference);
        if (booking == null) {
            throw new BadCredentialsException("Booking reference was not found.");
        }
        String otp = otpService.getIfPresent(reference);
        return new BookerUserDetails(reference, "622354");
    }
}
