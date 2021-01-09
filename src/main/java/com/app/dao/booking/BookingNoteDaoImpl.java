package com.app.dao.booking;

import com.app.model.booking.BookingNote;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingNoteDaoImpl implements BookingNoteDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookingNoteDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdate(BookingNote bookingNote) {
        sessionFactory.getCurrentSession().saveOrUpdate(bookingNote);
    }

    @Override
    public List<BookingNote> getNotes(Long bookingId, Long offset) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BookingNote.class);
        criteria.add(Restrictions.eq("booking.id", bookingId));
        if (offset != null) {
            criteria.add(Restrictions.gt("id", offset));
        }
        criteria.setFetchMode("user", FetchMode.JOIN);
        criteria.setMaxResults(10);
        return criteria.list();
    }
}
