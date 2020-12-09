package com.app.booking;

import com.app.dao.booking.BookingDao;
import com.app.dao.booking.BookingDaoImpl;
import com.app.dao.user.UserDao;
import com.app.dao.user.UserDaoImpl;
import com.app.model.booking.Booking;
import com.app.model.user.User;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContext-test.xml"})
@Transactional
public class BookingDaoImplTest extends TestCase {
    private BookingDao bookingDao;
    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setUp() {
        bookingDao = new BookingDaoImpl(sessionFactory);
        userDao = new UserDaoImpl(sessionFactory);
    }

    @Test
    public void shouldBook() {
        User user = createUser("email","phone");

        Booking booking = new Booking();
        booking.setUser(user);

        Date from = new DateTime().withYear(2020).withMonthOfYear(10).withDayOfMonth(1).withMillisOfDay(0).toDate();
        Date to = new DateTime().withYear(2020).withMonthOfYear(10).withDayOfMonth(3).withMillisOfDay(0).toDate();
        booking.setDateFrom(from);
        booking.setDateTo(to);
        booking.addNote(user, "NOTE");

        bookingDao.saveOrUpdate(booking);

        User user3 = createUser("email3","phone3");

        Booking booking2 = new Booking();
        booking2.setUser(user3);

        Date from2 = new DateTime().withYear(2020).withMonthOfYear(10).withDayOfMonth(1).withMillisOfDay(0).toDate();
        Date to2 = new DateTime().withYear(2020).withMonthOfYear(10).withDayOfMonth(4).withMillisOfDay(0).toDate();
        booking2.setDateFrom(from2);
        booking2.setDateTo(to2);

        bookingDao.saveOrUpdate(booking2);

        List<Booking> bookings = bookingDao.getAll();

        Booking dbBooking = bookings.get(0);

        int numberOfGuests = bookingDao.getNumberOfGuests(from, to);

        System.out.println("numberOfGuests" + numberOfGuests);
    }

    private User createUser(String email, String phone) {
        User user = new User();
        user.setEmail(email);
        user.setPhone(phone);
        userDao.save(user);
        return user;
    }
}