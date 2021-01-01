package com.app.dao.booking;

import com.app.model.booking.Booking;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
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

    @Override
    public Booking getById(Long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.idEq(id));
        return (Booking) criteria.uniqueResult();
    }

    @Override
    public List<Booking> getForDates(Date dateFromMin, Date dateFromMax,Date dateToMin, Date dateToMax) {
//        CriteriaQuery<Booking> query = sessionFactory.getCurrentSession().getCriteriaBuilder().createQuery(Booking.class);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        if (dateFromMin != null) {
            criteria.add(Restrictions.ge("dateFrom", dateFromMin));
        }
        if (dateFromMax != null) {
            criteria.add(Restrictions.le("dateFrom", dateFromMax));
        }
        if (dateToMin != null) {
            criteria.add(Restrictions.ge("dateTo", dateToMin));
        }
        if (dateToMax != null) {
            criteria.add(Restrictions.le("dateTo", dateToMax));
        }
        criteria.addOrder(Order.desc("dateFrom"));
        return criteria.list();
    }

    @Override
    public Booking getByReference(String reference) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.eq("reference", reference));
        return (Booking) criteria.uniqueResult();
    }

}
