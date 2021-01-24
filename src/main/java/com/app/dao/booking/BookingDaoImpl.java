package com.app.dao.booking;

import com.app.controller.admin.request.BookingFindRequest;
import com.app.model.booking.Booking;
import org.apache.commons.lang3.StringUtils;
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
        criteria.setFetchMode("user", FetchMode.JOIN);
        return (Booking) criteria.uniqueResult();
    }

    @Override
    public List<Booking> getForDates(BookingFindRequest bookingFindRequest) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class).createAlias("user","user");
        if (bookingFindRequest.getDateFromMin() != null) {
            criteria.add(Restrictions.ge("dateFrom", bookingFindRequest.getDateFromMin()));
        }
        if (bookingFindRequest.getDateFromMax() != null) {
            criteria.add(Restrictions.le("dateFrom", bookingFindRequest.getDateFromMax()));
        }
        if (bookingFindRequest.getDateToMin() != null) {
            criteria.add(Restrictions.ge("dateTo", bookingFindRequest.getDateToMin()));
        }
        if (bookingFindRequest.getDateToMax() != null) {
            criteria.add(Restrictions.le("dateTo", bookingFindRequest.getDateToMax()));
        }
        if (StringUtils.isNotBlank(bookingFindRequest.getEmail())) {
            criteria.add(Restrictions.eq("user.email", bookingFindRequest.getEmail()));
        }
        if (StringUtils.isNoneBlank(bookingFindRequest.getReference())) {
            criteria.add(Restrictions.eq("reference", bookingFindRequest.getReference()));
        }
        if (bookingFindRequest.getOffset() != null) {
            criteria.setFirstResult(bookingFindRequest.getOffset());
        }
        criteria.setMaxResults(bookingFindRequest.getPageSize());
        criteria.addOrder(Order.desc("dateFrom"));
        return criteria.list();
    }

    @Override
    public Booking getByReference(String reference) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.eq("reference", reference));
        criteria.setFetchMode("user", FetchMode.JOIN);
        return (Booking) criteria.uniqueResult();
    }

    @Override
    public List<Booking> getExistingBookings(Long userId, Date dateFrom, Date dateTo) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.eq("user.id", userId));
        criteria.add(Restrictions.ne("status", Booking.BookingStatus.CANCELLED));
        criteria.add(Restrictions.disjunction(
                Restrictions.conjunction(
                        Restrictions.ge("dateFrom", dateFrom),
                        Restrictions.le("dateFrom", dateTo)
                ),
                Restrictions.conjunction(
                        Restrictions.ge("dateTo", dateFrom),
                        Restrictions.le("dateTo", dateTo)
                ),
                Restrictions.conjunction(
                        Restrictions.le("dateFrom", dateFrom),
                        Restrictions.ge("dateTo", dateTo)
                )
        ));
        return criteria.list();
    }

    @Override
    public List<Booking> getBookingForReminder(Date dateFrom) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.eq("status", Booking.BookingStatus.APPROVED));
        criteria.add(Restrictions.eq("dateFrom", dateFrom));
        criteria.setFetchMode("user", FetchMode.JOIN);
        return criteria.list();
    }
}
