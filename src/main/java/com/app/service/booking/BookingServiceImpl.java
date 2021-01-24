package com.app.service.booking;

import com.app.controller.admin.request.BookingFindRequest;
import com.app.controller.booking.dto.BookingRequest;
import com.app.dao.booking.BookingDao;
import com.app.dao.booking.BookingNoteDao;
import com.app.model.booking.Booking;
import com.app.model.booking.BookingNote;
import com.app.model.user.User;
import com.app.service.jms.EmailMessage;
import com.app.service.jms.MessageProducer;
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
    private final MessageProducer messageProducer;

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao, BookingNoteDao bookingNoteDao, UserService userService, MessageProducer messageProducer) {
        this.bookingDao = bookingDao;
        this.bookingNoteDao = bookingNoteDao;
        this.userService = userService;
        this.messageProducer = messageProducer;
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
        booking.setContactName(bookingRequest.getContactName());
        booking.setPhone(bookingRequest.getPhone());
        booking.setStatus(bookingRequest.getStatus());
        saveOrUpdate(booking);
        if (StringUtils.isNotBlank(bookingRequest.getNote())) {
            saveNote(user, booking, bookingRequest.getNote());
        }
        // send email
        String html = "Hello " + bookingRequest.getContactName() + "\n" + "Booking Reference: " + booking.getReference() + "\nYou will be notified when your booking is examined and approved.";
        EmailMessage emailMessage = new EmailMessage(user.getEmail(), "Your Booking Details", html);
        messageProducer.sendMessage(MessageProducer.EMAIL_QUEUE_NAME, emailMessage);

        return booking;
    }

    @Override
    @Transactional
    public Booking saveOrUpdate(Booking booking) {
        bookingDao.saveOrUpdate(booking);
        return booking;
    }

    @Override
    @Transactional
    public Booking getById(Long id) {
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
    public List<BookingNote> getNotes(Long bookingId, Long offset) {
        return bookingNoteDao.getNotes(bookingId, offset);
    }

    @Override
    @Transactional
    public List<Booking> getForDates(BookingFindRequest bookingFindRequest) {
        return bookingDao.getForDates(bookingFindRequest);
    }

    @Override
    @Transactional
    public List<Booking> getExistingBookings(Long userId, Date dateFrom, Date dateTo) {
        return bookingDao.getExistingBookings(userId, dateFrom, dateTo);
    }

    @Override
    @Transactional
    public Booking getByReference(String reference) {
        return bookingDao.getByReference(reference);
    }

    @Override
    @Transactional
    public List<Booking> getBookingForReminder(Date dateFrom) {
        return bookingDao.getBookingForReminder(dateFrom);
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
