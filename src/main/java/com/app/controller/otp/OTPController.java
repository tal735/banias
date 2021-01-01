package com.app.controller.otp;

import com.app.controller.booking.dto.BookingDto;
import com.app.controller.booking.dto.BookingRequest;
import com.app.model.booking.Booking;
import com.app.model.user.SessionUser;
import com.app.service.booking.BookingService;
import com.app.service.email.EmailService;
import com.app.service.otp.OTPService;
import com.app.service.security.SecurityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class OTPController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OTPController.class);

    private final BookingService bookingService;
    private final OTPService otpService;
    private final EmailService emailService;

    @Autowired
    public OTPController(BookingService bookingService, OTPService otpService, EmailService emailService) {
        this.bookingService = bookingService;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public ResponseEntity test() {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setContactName("contact");
        bookingRequest.setEmail("email@email.com");
        bookingRequest.setGuests(10);
        bookingRequest.setStatus(Booking.BookingStatus.PENDING);
        bookingRequest.setDateFrom(new DateTime().minusDays(4).toDate());
        bookingRequest.setDateTo(new DateTime().minusDays(1).toDate());
        Booking booking = bookingService.book(1L, bookingRequest);
        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok().body(bookingDto);
    }

    @PostMapping(value = "/otp/request")
    @ResponseBody
    public ResponseEntity generateViewBookingOtp(@RequestParam String reference) {
        Booking booking = bookingService.getByReference(reference);
        if (booking == null) {
            return ResponseEntity.badRequest().body("Booking not found.");
        }
        String otp = otpService.generateOtp(reference);
        emailService.sendEmail(booking.getEmail(), "Your OTP is: " + otp);
        LOGGER.debug("Reference: " + reference + ", OTP: " + otp);
        return ResponseEntity.ok().build();
    }
}
