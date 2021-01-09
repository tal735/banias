package com.app.controller.booking;

import com.app.controller.booking.dto.BookingDto;
import com.app.controller.booking.dto.BookingRequest;
import com.app.controller.validator.BookingValidator;
import com.app.model.booking.Booking;
import com.app.service.security.SecurityUtils;
import com.app.service.booking.BookingService;
import com.app.service.user.SessionUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/api/booking")
public class BookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;
    private final BookingValidator bookingValidator;

    @Autowired
    public BookingController(BookingService bookingService, BookingValidator bookingValidator) {
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
    }

    @GetMapping("/{reference}")
    @ResponseBody
    public ResponseEntity getBooking(@PathVariable String reference) {
        Long userId = SecurityUtils.getLoggedInUser().getUserId();
        Booking booking = bookingService.getByReference(reference);
        if (booking == null || !booking.getUser().getId().equals(userId)) {
            return ResponseEntity.badRequest().body("Booking does not belong to user.");
        }
        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok().body(bookingDto);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity book(@RequestBody BookingRequest bookingRequest, HttpServletRequest httpServletRequest) throws Exception {
        SessionUserDetails user = SecurityUtils.getLoggedInUser();
        Map<String, String> errors = bookingValidator.validate(user, null, bookingRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Booking booking = bookingService.book(user.getUserId(), bookingRequest);
        Long bookingId = booking.getId();
        booking = bookingService.getById(bookingId);
        BookingDto bookingDto = new BookingDto(booking);
        httpServletRequest.logout();
        return ResponseEntity.ok().body(bookingDto);
    }

    @PostMapping("/{reference}")
    @ResponseBody
    public ResponseEntity updateBooking(@PathVariable String reference, @RequestBody BookingRequest bookingRequest) {
        SessionUserDetails user = SecurityUtils.getLoggedInUser();
        Map<String, String> errors = bookingValidator.validate(user, reference, bookingRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Booking booking = bookingService.getByReference(reference);
        booking.setDateFrom(bookingRequest.getDateFrom());
        booking.setDateTo(bookingRequest.getDateTo());
        booking.setGuests(bookingRequest.getGuests());
        booking.setContactName(bookingRequest.getContactName());
        booking.setPhone(bookingRequest.getPhone());
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setDateModified(new Date());
        bookingService.saveOrUpdate(booking);

        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok(bookingDto);
    }
}
