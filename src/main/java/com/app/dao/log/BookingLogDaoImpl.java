package com.app.dao.log;

import com.app.model.log.BookingLog;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookingLogDaoImpl implements BookingLogDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookingLogDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdate(BookingLog bookingLog) {
        sessionFactory.getCurrentSession().saveOrUpdate(bookingLog);
    }
}
