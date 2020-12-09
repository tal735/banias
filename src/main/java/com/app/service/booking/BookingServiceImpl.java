package com.app.service.booking;

import com.app.controller.booking.BookingRequest;
import com.app.dao.booking.BookingDao;
import com.app.model.booking.Booking;
import com.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    @Transactional
    public Booking book(User user, BookingRequest bookingRequest) {
        Booking booking = new Booking();

        booking.setUser(user);
        booking.setDateFrom(bookingRequest.getDateFrom());
        booking.setDateTo(bookingRequest.getDateTo());
        booking.setGuests(bookingRequest.getGuests());
        booking.setStatus("PENDING");
        booking.addNote(user, bookingRequest.getNote());

        bookingDao.saveOrUpdate(booking);

        return booking;

    }

    @Override
    @Transactional
    public List<Booking> getAll() {
        return bookingDao.getAll();
    }
}
