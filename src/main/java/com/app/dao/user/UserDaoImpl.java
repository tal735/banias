package com.app.dao.user;

import com.app.model.user.User;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public User getByEmail(String email) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        criteria.setFetchMode("roles", FetchMode.JOIN);
        return (User) criteria.uniqueResult();
    }

    @Override
    public User getById(Long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.idEq(id));
        criteria.setFetchMode("roles", FetchMode.JOIN);
        return (User) criteria.uniqueResult();
    }

    @Override
    public int getCount() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        return count.intValue();
    }
}
