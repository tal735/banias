package com.app.dao.booking;

import com.app.model.booking.Booking;

import java.util.Date;
import java.util.List;

public interface BookingDao {
    void saveOrUpdate(Booking booking);
    List<Booking> getBookings(Long userId, Integer offset);
    Booking getById(Long id);
    List<Booking> getExistingBookings(Long userId, Date dateFrom, Date dateTo);
}
