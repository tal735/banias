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
        return sessionFactory.getCurrentSession().get(Booking.class, id);
    }

    @Override
    public List<Booking> getExistingBookings(Long userId, Date dateFrom, Date dateTo) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.add(Restrictions.eq("user.id", userId));
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
    public List<Booking> getForDates(Date dateFrom, Date dateTo) {
//        CriteriaQuery<Booking> query = sessionFactory.getCurrentSession().getCriteriaBuilder().createQuery(Booking.class);
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        if (dateFrom != null) {
            criteria.add(Restrictions.ge("dateFrom", dateFrom));
        }
        if (dateTo != null) {
            criteria.add(Restrictions.le("dateTo", dateTo));
        }
        criteria.setFetchMode("user", FetchMode.JOIN);
        criteria.addOrder(Order.desc("dateFrom"));
//        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
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
