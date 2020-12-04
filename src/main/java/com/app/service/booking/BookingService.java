package com.app.service.booking;

import com.app.controller.booking.BookingRequest;
import com.app.model.booking.Booking;
import com.app.model.user.User;

import java.util.Date;
import java.util.List;

public interface BookingService {
    Booking book(BookingRequest bookingRequest);

    List<Booking> getAll();
}
