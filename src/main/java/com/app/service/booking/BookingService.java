package com.app.service.booking;

import com.app.controller.admin.request.BookingFindRequest;
import com.app.controller.booking.dto.BookingRequest;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;

import java.util.Date;
import java.util.List;

public interface BookingService {
    Booking book(Long userId, BookingRequest bookingRequest);
    Booking saveOrUpdate(Booking booking);
    Booking cancelBooking(Long bookingId);
    Booking getById(Long id);
    BookingNote addNote(Long userId, Long bookingId, String note);
    List<BookingNote> getNotes(Long bookingId, Long offset);
    List<Booking> getForDates(BookingFindRequest bookingFindRequest);
    List<Booking> getExistingBookings(Long userId, Date dateFrom, Date dateTo);
    Booking getByReference(String reference);
    List<Booking> getBookingForReminder(Date dateFrom);
    Booking updateBookingById(Long bookingId, BookingRequest bookingRequest);
    Booking updateBookingByReference(String reference, BookingRequest bookingRequest);
}
