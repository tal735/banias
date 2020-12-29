package com.app.service.booking;

import com.app.controller.booking.dto.BookingRequest;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;

import java.util.Date;
import java.util.List;

public interface BookingService {
    Booking book(Long userId, BookingRequest bookingRequest);
    Booking saveOrUpdate(Booking booking);
    List<Booking> getBookings(Long userId, Integer offset);
    Booking getBookingById(Long id);
    BookingNote addNote(Long userId, Long bookingId, String note);
    List<BookingNote> getNotes(Long bookingId, Integer offset);
    List<Booking> getExistingBookings(Long userId, Date dateFrom, Date dateTo);
    List<Booking> getForDates(Date dateFromMin, Date dateFromMax, Date dateToMin, Date dateToMax);
}
