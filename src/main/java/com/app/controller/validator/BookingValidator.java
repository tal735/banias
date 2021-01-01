package com.app.controller.validator;

import com.app.controller.booking.dto.BookingRequest;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

@Component
public class BookingValidator {

    private final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");

    public Map<String, String> checkForErrors(BookingRequest bookingRequest) {
        Map<String, String> errors = Maps.newHashMap();

        if (bookingRequest.getEmail() == null ||
                !EMAIL_PATTERN.matcher(bookingRequest.getEmail()).matches()) {
            errors.put("error", "Email is not valid.");
        } else if (StringUtils.length(bookingRequest.getContactName()) < 3) {
            errors.put("error", "Please enter a valid name.");
        } else if (bookingRequest.getDateFrom() == null || bookingRequest.getDateTo() == null) {
            errors.put("error", "Please enter valid dates.");
        } else if (bookingRequest.getDateFrom().after(bookingRequest.getDateTo())) {
            errors.put("error", "Please enter valid dates range.");
        } else if (bookingRequest.getGuests() == null || bookingRequest.getGuests() <= 0) {
            errors.put("error", "Please check your number of guests.");
        }

        return errors;
    }
}
