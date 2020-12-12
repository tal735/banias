package com.app.controller.booking;

import com.app.model.booking.Booking;
import com.app.model.user.SessionUser;
import com.app.security.SecurityUtils;
import com.app.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @ResponseBody
    public List<BookingDto> getBookings(@RequestParam(required = false, defaultValue = "0") Integer offset) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        List<Booking> bookings = bookingService.getBookings(user.getUserId(), offset);
        List<BookingDto> bookingDtos = bookings.stream().map(BookingDto::new).collect(Collectors.toList());
        return bookingDtos;
    }

    @PostMapping
    @ResponseBody
    public Booking book(@RequestBody BookingRequest bookingRequest) {
        SessionUser user = SecurityUtils.getLoggedInUser();
        return bookingService.book(user.getUserId(), bookingRequest);
    }
}
