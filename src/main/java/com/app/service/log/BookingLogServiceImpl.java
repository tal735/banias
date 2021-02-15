package com.app.service.log;

import com.app.dao.booking.BookingDao;
import com.app.dao.log.BookingLogDao;
import com.app.model.booking.Booking;
import com.app.model.log.BookingLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookingLogServiceImpl implements BookingLogService {

    private final BookingDao bookingDao;
    private final BookingLogDao bookingLogDao;

    @Autowired
    public BookingLogServiceImpl(BookingDao bookingDao, BookingLogDao bookingLogDao) {
        this.bookingDao = bookingDao;
        this.bookingLogDao = bookingLogDao;
    }

    @Override
    @Transactional
    public void log(LogMessage log) {
        Booking booking = bookingDao.getById(log.getId());

        BookingLog bookingLog = new BookingLog();
        bookingLog.setBooking(booking);
        bookingLog.setDate(log.getTimestamp());
        bookingLog.setLog(log.getText());

        bookingLogDao.saveOrUpdate(bookingLog);
    }
}
