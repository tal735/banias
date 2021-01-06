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

    @GetMapping
    @ResponseBody
    public ResponseEntity<BookingDto> getBooking() {
        Long bookingId = SecurityUtils.getBookingIdFromAuthentication();
        Booking booking = bookingService.getById(bookingId);
        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok().body(bookingDto);
    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity book(@RequestBody BookingRequest bookingRequest, HttpServletRequest httpServletRequest) throws Exception {
        SessionUserDetails user = SecurityUtils.getLoggedInUser();
        Map<String, String> errors = bookingValidator.validate(user, bookingRequest);
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

    @PostMapping
    @ResponseBody
    public ResponseEntity updateBooking(@RequestBody BookingRequest bookingRequest) {
        SessionUserDetails user = SecurityUtils.getLoggedInUser();
        Map<String, String> errors = bookingValidator.validate(user, bookingRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Long bookingId = SecurityUtils.getBookingIdFromAuthentication();
        Booking booking = bookingService.getById(bookingId);
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
