package com.app.controller.admin;

import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/booking")
public class AdminBookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminBookingController.class);

    private final BookingService bookingService;

    @Autowired
    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
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
}
