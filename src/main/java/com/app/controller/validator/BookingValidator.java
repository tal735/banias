package com.app.controller.validator;

import com.app.controller.booking.dto.BookingRequest;
import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BookingValidator {

    private final BookingService bookingService;

    @Autowired
    public BookingValidator(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Map<String, String> checkForErrors(Long userId, Long bookingId, BookingRequest bookingRequest) {
        Map<String, String> errors = Maps.newHashMap();

        if (bookingRequest.getDateFrom() == null || bookingRequest.getDateTo() == null) {
            errors.put("error", "Please enter valid dates.");
        } else if (bookingRequest.getDateFrom().after(bookingRequest.getDateTo())) {
            errors.put("error", "Please enter valid dates range.");
        } else if (bookingRequest.getGuests() == null || bookingRequest.getGuests() <= 0) {
            errors.put("error", "Please check your number of guests.");
        } else {
            List<Booking> bookings = bookingService.getExistingBookings(userId,
                    bookingRequest.getDateFrom(), bookingRequest.getDateTo());
            if (bookingId == null) {
                if (!bookings.isEmpty()) {
                    errors.put("error", "There is another booking during these dates.");
                }
            } else {
                if (bookings.size() > 1) {
                    errors.put("error", "There is another booking during these dates.");
                } else if (bookings.size() == 1) {
                    if (!bookings.get(0).getId().equals(bookingId)) {
                        errors.put("error", "There is another booking during these dates.");
                    }
                }
            }
        }

        return errors;
    }
}
