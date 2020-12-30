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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @PostMapping
    @ResponseBody
    public ResponseEntity book(@RequestBody BookingRequest bookingRequest) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Map<String, String> errors = bookingValidator.checkForErrors(user.getUserId(), null, bookingRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Booking booking = bookingService.book(user.getUserId(), bookingRequest);
        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok().body(bookingDto);
    }

    @PostMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity updateBooking(@PathVariable(name = "id") Long bookingId, @RequestBody BookingRequest bookingRequest) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Booking booking = bookingService.getBookingById(bookingId);

        if (booking == null || (!SecurityUtils.isCurrentUserAdmin()
                && !booking.getUser().getId().equals(user.getUserId()))) {
            LOGGER.warn("updateBooking: User " + user.getUserId() + " is trying to access booking "
                    + booking + ", but doesn't have access.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Map<String, String> errors = bookingValidator.checkForErrors(booking.getUser().getId(), bookingId, bookingRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        booking.setDateFrom(bookingRequest.getDateFrom());
        booking.setDateTo(bookingRequest.getDateTo());
        booking.setGuests(bookingRequest.getGuests());
        booking.setDateModified(new Date());
        if (SecurityUtils.isCurrentUserAdmin()) {
            if (bookingRequest.getStatus() != null) {
                booking.setStatus(bookingRequest.getStatus());
            }
        } else {
            booking.setStatus(Booking.BookingStatus.PENDING);
        }
        bookingService.saveOrUpdate(booking);

        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok(bookingDto);
    }

    @GetMapping
    @ResponseBody
    public List<BookingDto> getBookings(@RequestParam(required = false, defaultValue = "0") Integer offset) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        List<Booking> bookings = bookingService.getBookings(user.getUserId(), offset);
        List<BookingDto> bookingDtos = bookings.stream().map(BookingDto::new).collect(Collectors.toList());
        return bookingDtos;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long id) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        Booking booking = bookingService.getBookingById(id);

        if (booking == null || (!SecurityUtils.isCurrentUserAdmin()
                && !booking.getUser().getId().equals(user.getUserId()))) {
            LOGGER.warn("getBookings: User " + user.getUserId() + " is trying to access booking "
                    + booking + ", but doesn't have access.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok().body(bookingDto);
    }

}
