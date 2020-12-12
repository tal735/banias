package com.app.service.booking;

import com.app.controller.booking.BookingRequest;
import com.app.model.booking.Booking;

import java.util.List;

public interface BookingService {
    Booking book(Long userId, BookingRequest bookingRequest);
    List<Booking> getBookings(Long userId, Integer offset);
}
