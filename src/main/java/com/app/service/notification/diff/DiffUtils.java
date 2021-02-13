package com.app.service.notification.diff;

import com.app.controller.booking.dto.BookingRequest;
import com.app.model.booking.Booking;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class DiffUtils {
    public static List<Diff> findDiffs(Booking booking, BookingRequest bookingRequest) {
        List<Diff> diffs = Lists.newArrayList();
        if (datesNotEqual(booking.getDateFrom(), bookingRequest.getDateFrom())) {
            diffs.add(new Diff("Arrival Date", new DateTime(booking.getDateFrom()).toString("dd-MM-yyyy"), new DateTime(bookingRequest.getDateFrom()).toString("dd-MM-yyyy")));
        }
        if (datesNotEqual(booking.getDateTo(), bookingRequest.getDateTo())) {
            diffs.add(new Diff("Departure Date", new DateTime(booking.getDateTo()).toString("dd-MM-yyyy"), new DateTime(bookingRequest.getDateTo()).toString("dd-MM-yyyy")));
        }
        if (ObjectUtils.notEqual(booking.getGuests(), bookingRequest.getGuests())) {
            diffs.add(new Diff("Guests", booking.getGuests() + "", bookingRequest.getGuests() + ""));
        }
        if (ObjectUtils.notEqual(booking.getContactName(), bookingRequest.getContactName())) {
            diffs.add(new Diff("Contact Name", booking.getContactName(), bookingRequest.getContactName()));
        }
        if (ObjectUtils.notEqual(booking.getPhone(), bookingRequest.getPhone())) {
            diffs.add(new Diff("Phone", booking.getPhone(), bookingRequest.getPhone()));
        }
        if (ObjectUtils.notEqual(booking.getStatus(), bookingRequest.getStatus())) {
            diffs.add(new Diff("Status", booking.getStatus().name(), bookingRequest.getStatus().name()));
        }
        return diffs;
    }

    private static boolean datesNotEqual(Date date1, Date date2) {
        return ObjectUtils.notEqual(new DateTime(date1), new DateTime(date2));
    }
}
