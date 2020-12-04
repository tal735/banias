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
        sessionFactory.getCurrentSession().saveOrUpdate(booking);
    }

    @Override
    public List<Booking> getAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        criteria.setFetchMode("members", FetchMode.JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public int getNumberOfGuests(Date from, Date to) {
        String sql = "select count(distinct(bm.user_id)) as count " +
                "from booking b inner join booking_member bm on b.id = bm.booking_id " +
                "where (b.date_from >= :from and b.date_from <= :to)  or (b.date_to >= :from and b.date_to <= :to)";
        NativeQuery query = sessionFactory.getCurrentSession().createNativeQuery(sql);
        query.setParameter("from", from);
        query.setParameter("to", to);
        query.addScalar("count", new IntegerType());
        Integer count = (Integer) query.getSingleResult();
        return count;
    }
}
