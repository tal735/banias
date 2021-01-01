package com.app.controller.booking;

import com.app.controller.booking.dto.BookingDto;
import com.app.controller.booking.dto.BookingRequest;
import com.app.controller.validator.BookingValidator;
import com.app.model.booking.Booking;
import com.app.model.user.SessionUser;
import com.app.service.security.SecurityUtils;
import com.app.service.booking.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @ResponseBody
    public ResponseEntity<BookingDto> getBooking() {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Long id = null;
        Booking booking = bookingService.getBookingById(id);
        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok().body(bookingDto);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity book(@RequestBody BookingRequest bookingRequest) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Map<String, String> errors = bookingValidator.checkForErrors(bookingRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Booking booking = bookingService.book(user.getUserId(), bookingRequest);
        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok().body(bookingDto);
    }

    @PatchMapping
    @ResponseBody
    public ResponseEntity updateBooking(@RequestBody BookingRequest bookingRequest) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Long bookingId = null;
        Booking booking = bookingService.getBookingById(bookingId);

        Map<String, String> errors = bookingValidator.checkForErrors(bookingRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        booking.setDateFrom(bookingRequest.getDateFrom());
        booking.setDateTo(bookingRequest.getDateTo());
        booking.setGuests(bookingRequest.getGuests());
        booking.setDateModified(new Date());
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setContactName(bookingRequest.getContactName());
        bookingService.saveOrUpdate(booking);

        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok(bookingDto);
    }

}
