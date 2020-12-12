package com.app.service.booking;

import com.app.controller.booking.dto.BookingRequest;
import com.app.dao.booking.BookingDao;
import com.app.dao.booking.BookingNoteDao;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.model.user.User;
import com.app.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final BookingNoteDao bookingNoteDao;
    private final UserService userService;

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao, BookingNoteDao bookingNoteDao, UserService userService) {
        this.bookingDao = bookingDao;
        this.bookingNoteDao = bookingNoteDao;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Booking book(Long userId, BookingRequest bookingRequest) {
        User user = userService.getById(userId);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setDateFrom(bookingRequest.getDateFrom());
        booking.setDateTo(bookingRequest.getDateTo());
        booking.setGuests(bookingRequest.getGuests());
        booking.setStatus("PENDING");
        bookingDao.saveOrUpdate(booking);
        if (StringUtils.isNotBlank(bookingRequest.getNote())) {
            saveNote(user, booking, bookingRequest.getNote());
        }
        return booking;
    }

    @Override
    @Transactional
    public List<Booking> getBookings(Long userId, Integer offset) {
        return bookingDao.getBookings(userId, offset);
    }

    @Override
    @Transactional
    public Booking getBookingById(Long id) {
        return bookingDao.getById(id);
    }

    @Override
    @Transactional
    public BookingNote addNote(Long userId, Long bookingId, String note) {
        User user = userService.getById(userId);
        Booking booking = bookingDao.getById(bookingId);
        return saveNote(user, booking, note);
    }

    @Override
    @Transactional
    public List<BookingNote> getNotes(Long bookingId, Integer offset) {
        return bookingNoteDao.getNotes(bookingId, offset);
    }

    private BookingNote saveNote(User user, Booking booking, String note) {
        BookingNote bookingNote = new BookingNote();
        bookingNote.setBooking(booking);
        bookingNote.setUser(user);
        bookingNote.setDate(new Date());
        bookingNote.setNote(note);
        bookingNoteDao.saveOrUpdate(bookingNote);
        return bookingNote;
    }
}
