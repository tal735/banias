package com.app.controller.validator;

import com.app.controller.booking.dto.BookingRequest;
import com.app.model.booking.Booking;
import com.app.service.booking.BookingService;
import com.app.service.user.SessionUserDetails;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
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

    public Map<String, String> validate(SessionUserDetails user, BookingRequest bookingRequest) {
        Map<String, String> errors = Maps.newHashMap();

        if (StringUtils.isBlank(bookingRequest.getPhone())) {
            errors.put("error", "Please enter a valid phone number.");
        } else if (StringUtils.length(bookingRequest.getContactName()) < 3) {
            errors.put("error", "Please enter a valid name.");
        } else if (bookingRequest.getDateFrom() == null || bookingRequest.getDateTo() == null) {
            errors.put("error", "Please enter valid dates.");
        } else if (bookingRequest.getDateFrom().after(bookingRequest.getDateTo())) {
            errors.put("error", "Please enter valid dates range.");
        } else if (bookingRequest.getGuests() == null || bookingRequest.getGuests() <= 0) {
            errors.put("error", "Please check your number of guests.");
        } else {
            List<Booking> bookings = bookingService.getExistingBookings(user.getUserId(),
                    bookingRequest.getDateFrom(), bookingRequest.getDateTo());
            Long bookingId = user.getBookingId();
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
