package com.app.dao.password;

import com.app.model.password.PasswordReset;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordDaoImpl implements PasswordDao {

    final SessionFactory sessionFactory;

    @Autowired
    public PasswordDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(PasswordReset passwordReset) {
        sessionFactory.getCurrentSession().saveOrUpdate(passwordReset);
    }

    @Override
    public PasswordReset getByUserId(Long userId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PasswordReset.class);
        criteria.add(Restrictions.eq("user.id", userId));
        return (PasswordReset) criteria.uniqueResult();
    }

    @Override
    public void deleteAllForUser(Long userId) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from PasswordReset where user.id = :id")
                .setParameter("id", userId)
                .executeUpdate();
    }
}
