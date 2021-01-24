package com.app.controller.otp;

import com.app.controller.validator.EmailValidator;
import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import com.app.service.jms.EmailMessage;
import com.app.service.jms.MessageProducer;
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
    private final MessageProducer messageProducer;

    @Autowired
    public OTPController(UserService userService, BookingService bookingService, OTPService otpService, MessageProducer messageProducer) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.otpService = otpService;
        this.messageProducer = messageProducer;
    }

    @PostMapping(value = "/otp/book")
    @ResponseBody
    public ResponseEntity generateNewBookingOtp(@RequestBody String email) {
        if (!EmailValidator.isValid(email)) {
            return ResponseEntity.badRequest().body("Email is invalid");
        }
        String userEmail = email.toLowerCase().trim();
        userService.getOrCreateUser(userEmail);
        String otp = otpService.generateOtp(userEmail);
        dispatchEmail(userEmail, otp);
        LOGGER.debug("Email: " + email + ", OTP: " + otp);
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
        String otp = otpService.generateOtp(userEmail);
        dispatchEmail(userEmail, otp);
        LOGGER.debug("Reference: " + reference + ", OTP: " + otp);
        return ResponseEntity.ok().build();
    }

    private void dispatchEmail(String email, String otp) {
        EmailMessage message = new EmailMessage(email, "Your OTP", "Your OTP is: " + otp);
        messageProducer.sendMessage(MessageProducer.EMAIL_QUEUE_NAME, message);
    }
}
