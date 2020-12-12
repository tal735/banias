package com.app.dao.booking;

import com.app.model.booking.Booking;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class BookingDaoImpl implements BookingDao {


    private final SessionFactory sessionFactory;

    @Autowired
    public BookingDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdate(Booking booking) {
        if (booking.getId() == null) {
            sessionFactory.getCurrentSession().saveOrUpdate(booking);
        } else {
            Booking dbBooking = getById(booking.getId());
            dbBooking.setDateModified(new Date());
            sessionFactory.getCurrentSession().merge(booking);
        }
    }

    public Booking getById(Long id) {
        return sessionFactory.getCurrentSession().get(Booking.class, id);
    }

    @Override
    public List<Booking> getBookings(Long userId, Integer offset) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.eq("user.id", userId));
        criteria.addOrder(Order.desc("dateFrom"));
        criteria.setFirstResult(offset);
        criteria.setMaxResults(10);
        return criteria.list();
    }

}
