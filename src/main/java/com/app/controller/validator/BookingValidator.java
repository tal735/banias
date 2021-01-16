package com.app.controller.validator;

import com.app.controller.booking.dto.BookingRequest;
import com.app.model.booking.Booking;
import com.app.model.user.User;
import com.app.service.booking.BookingService;
import com.app.service.user.UserService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BookingValidator {

    private final BookingService bookingService;
    private final UserService userService;

    @Autowired
    public BookingValidator(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    public Map<String, String> validate(Long userId, String bookingReference, BookingRequest bookingRequest) {
        Map<String, String> errors = Maps.newHashMap();

        if (StringUtils.isNotBlank(bookingReference)) {
            Booking booking = bookingService.getByReference(bookingReference);
            if (booking == null || !booking.getUser().getId().equals(userId)) {
                errors.put("error", "Booking does not belong to user.");
            }
        }

        validateCommon(bookingRequest, errors);

        if (errors.isEmpty()) {
            List<Booking> bookings = bookingService.getExistingBookings(userId,
                    bookingRequest.getDateFrom(), bookingRequest.getDateTo());
            if (StringUtils.isBlank(bookingReference)) {
                if (!bookings.isEmpty()) {
                    errors.put("error", "There is another booking during these dates.");
                }
            } else {
                if (bookings.size() > 1) {
                    errors.put("error", "There is another booking during these dates.");
                } else if (bookings.size() == 1) {
                    if (!bookings.get(0).getReference().equals(bookingReference)) {
                        errors.put("error", "There is another booking during these dates.");
                    }
                }
            }
        }

        return errors;
    }

    public Map<String, String> validateForAdminUpdate(Long bookingId, BookingRequest bookingRequest) {
        Map<String, String> errors = Maps.newHashMap();

        validateCommon(bookingRequest, errors);

        if (errors.isEmpty()) {
            List<Booking> bookings = bookingService.getExistingBookings(bookingRequest.getUserId(),
                    bookingRequest.getDateFrom(), bookingRequest.getDateTo());
            if (bookings.size() > 1) {
                errors.put("error", "There is another booking during these dates.");
            } else if (bookings.size() == 1) {
                if (!bookings.get(0).getId().equals(bookingId)) {
                    errors.put("error", "There is another booking during these dates.");
                }
            }
        }

        return errors;
    }

    public Map<String, String> validateForAdminAdd(BookingRequest addRequest) {
        Map<String, String> errors = Maps.newHashMap();

        validateCommon(addRequest, errors);

        if (errors.isEmpty()) {
            if (StringUtils.isNotBlank(addRequest.getEmail()) && !EmailValidator.isValid(addRequest.getEmail())) {
                errors.put("error", "Please enter a valid email.");
            }
        }

        if (errors.isEmpty()) {
            // if user exists, check if there is a booking already
            String email = addRequest.getEmail().toLowerCase().trim();
            User user = userService.getByEmail(email);
            if (user != null) {
                List<Booking> bookings = bookingService.getExistingBookings(user.getId(),
                        addRequest.getDateFrom(), addRequest.getDateTo());
                if (!bookings.isEmpty()) {
                    errors.put("error", "There is another booking during these dates.");
                }
            }
        }

        return errors;
    }

    private void validateCommon(BookingRequest bookingRequest, Map<String, String> errors) {
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
        }
    }
}
