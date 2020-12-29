package com.app.controller.admin;

import com.app.controller.booking.dto.BookingDto;
import com.app.controller.booking.dto.BookingRequest;
import com.app.controller.validator.BookingValidator;
import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/booking")
public class AdminBookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminBookingController.class);

    private final BookingService bookingService;
    private final BookingValidator bookingValidator;

    @Autowired
    public AdminBookingController(BookingService bookingService, BookingValidator bookingValidator) {
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
    }

    @PostMapping(value = "/find")
    @ResponseBody
    public List<AdminBookingDto> findBookings(@RequestBody BookingFindRequest bookingFindRequest) {
        List<Booking> bookings = bookingService.getForDates(
                bookingFindRequest.getDateFromMin(),
                bookingFindRequest.getDateFromMax(),
                bookingFindRequest.getDateToMin(),
                bookingFindRequest.getDateToMax());
        return bookings.stream().map(AdminBookingDto::new).collect(Collectors.toList());
    }

    @PostMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity book(@PathVariable(name = "id") Long bookingId, @RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.getBookingById(bookingId);

        Map<String, String> errors = bookingValidator.checkForErrors(booking.getUser().getId(),bookingId, bookingRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        booking.setDateFrom(bookingRequest.getDateFrom());
        booking.setDateTo(bookingRequest.getDateTo());
        booking.setGuests(bookingRequest.getGuests());
        booking.setStatus(bookingRequest.getStatus());
        booking.setDateModified(new Date());
        bookingService.saveOrUpdate(booking);

        BookingDto bookingDto = new BookingDto(booking);
        return ResponseEntity.ok(bookingDto);
    }

}
