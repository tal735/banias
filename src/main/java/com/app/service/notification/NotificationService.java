package com.app.service.notification;

import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.service.notification.diff.Diff;

import java.util.List;

public interface NotificationService {
    void notifyBookingAdded(Booking booking);
    void notifyBookingCancelled(Booking booking);
    void notifyBookingModified(Booking booking, List<Diff> diffs);
    void notifyNoteAdded(BookingNote bookingNote);
    void notifyOtp(String email, String otp);
    void notifyArrivalReminder(Booking booking);
}
