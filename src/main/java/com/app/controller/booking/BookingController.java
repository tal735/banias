package com.app.controller.booking;

import com.app.model.booking.Booking;
import com.app.model.user.User;
import com.app.service.booking.BookingService;
import com.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    public Booking book (@RequestBody BookingRequest bookingRequest) {
        User user = userService.getFirst();
        return bookingService.book(user, bookingRequest);
    }

}
