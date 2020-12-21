package com.app.controller.admin;

import com.app.controller.booking.dto.BookingDto;
import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminBookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminBookingController.class);

    private final BookingService bookingService;

    @Autowired
    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(value = "/bookings")
    @ResponseBody
    public List<BookingDto> findBookings(@RequestParam(required = false) Date dateFrom,
                                 @RequestParam(required = false) Date dateTo) {
        LOGGER.debug("Admin findBookings: dateFrom " + dateFrom + ", dateTo " + dateTo);
        List<Booking> bookings = bookingService.getForDates(dateFrom, dateTo);
        return bookings.stream().map(BookingDto::new).collect(Collectors.toList());
    }
}
