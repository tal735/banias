package com.app.dao.booking;

import com.app.model.booking.Booking;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.IntegerType;
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
    public List<Booking> getAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

}
