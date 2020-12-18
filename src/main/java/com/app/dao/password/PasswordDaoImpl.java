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
    public PasswordReset getByToken(String token) {
        if (token == null) {
            return null;
        }
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PasswordReset.class);
        criteria.add(Restrictions.eq("token", token));
        criteria.add(Restrictions.eq("used", false));
        return (PasswordReset) criteria.uniqueResult();
    }
}
