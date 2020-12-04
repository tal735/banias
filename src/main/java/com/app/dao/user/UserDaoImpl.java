package com.app.dao.user;

import com.app.model.user.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public User getFirst() {
        List list = sessionFactory.getCurrentSession().createCriteria(User.class).list();
        if (list.isEmpty()) {
            return null;
        }
        return (User) list.get(0);
    }
}
