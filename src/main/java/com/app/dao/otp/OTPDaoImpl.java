package com.app.dao.otp;

import com.app.model.otp.OTP;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OTPDaoImpl implements OTPDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public OTPDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrUpdate(OTP otp) {
        sessionFactory.getCurrentSession().saveOrUpdate(otp);
    }

    @Override
    public OTP getByReference(String reference) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OTP.class);
        criteria.add(Restrictions.eq("reference", reference));
        return (OTP) criteria.uniqueResult();
    }

    @Override
    public void delete(String reference) {
        sessionFactory.getCurrentSession()
                .createSQLQuery("delete from OTP where reference = :r")
                .setParameter("r", reference)
                .executeUpdate();
    }
}
