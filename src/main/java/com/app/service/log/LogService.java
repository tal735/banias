package com.app.service.log;

import com.app.model.booking.Booking;
import com.app.service.notification.diff.Diff;

import java.util.List;

public interface LogService {
    void logBookingAdded(Booking booking);
    void logBookingCancelled(Booking booking);
    void logBookingModified(Booking booking, List<Diff> diffs);
}
