package com.app.dao.booking;

import com.app.controller.admin.request.BookingFindRequest;
import com.app.model.booking.Booking;

import java.util.Date;
import java.util.List;

public interface BookingDao {
    void saveOrUpdate(Booking booking);
    Booking getById(Long id);
    List<Booking> getForDates(BookingFindRequest bookingFindRequest);
    Booking getByReference(String reference);
    List<Booking> getExistingBookings(Long userId, Date dateFrom, Date dateTo);
}
