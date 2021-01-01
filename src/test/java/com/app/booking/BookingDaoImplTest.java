package com.app.booking;

import com.app.dao.booking.BookingDao;
import com.app.dao.booking.BookingDaoImpl;
import com.app.dao.booking.BookingNoteDao;
import com.app.dao.booking.BookingNoteDaoImpl;
import com.app.dao.user.UserDao;
import com.app.dao.user.UserDaoImpl;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContext-test.xml"})
@Transactional
public class BookingDaoImplTest extends TestCase {
    private BookingNoteDao bookingNoteDao;
    private BookingDao bookingDao;
    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setUp() {
        bookingNoteDao = new BookingNoteDaoImpl(sessionFactory);
        bookingDao = new BookingDaoImpl(sessionFactory);
        userDao = new UserDaoImpl(sessionFactory);
    }

    @Test
    public void shouldBook() {

    }

}