package com.app.controller.otp;

import com.app.controller.validator.EmailValidator;
import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import com.app.service.notification.NotificationService;
import com.app.service.otp.OTPService;
import com.app.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OTPController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OTPController.class);

    private final UserService userService;
    private final BookingService bookingService;
    private final OTPService otpService;
    private final NotificationService notificationService;

    @Autowired
    public OTPController(UserService userService, BookingService bookingService, OTPService otpService, NotificationService notificationService) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.otpService = otpService;
        this.notificationService = notificationService;
    }

    @PostMapping(value = "/otp/book")
    @ResponseBody
    public ResponseEntity generateNewBookingOtp(@RequestBody String email) {
        if (!EmailValidator.isValid(email)) {
            return ResponseEntity.badRequest().body("Email is invalid");
        }
        String userEmail = email.toLowerCase().trim();
        sendOtp(userEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/otp/view")
    @ResponseBody
    public ResponseEntity generateViewBookingOtp(@RequestBody String reference) {
        Booking booking = bookingService.getByReference(reference);
        if (booking == null) {
            return ResponseEntity.badRequest().body("Reference number not found.");
        }
        String userEmail = booking.getUser().getEmail();
        sendOtp(userEmail);
        return ResponseEntity.ok().build();
    }

    private void sendOtp(String userEmail) {
        userService.getOrCreateUser(userEmail);
        String otp = otpService.generateOtp(userEmail);
        notificationService.notifyOtp(userEmail, otp);
    }
}
