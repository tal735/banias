package com.app.user;

import com.app.dao.user.UserDao;
import com.app.dao.user.UserDaoImpl;
import com.app.model.user.User;
import junit.framework.TestCase;
import org.hamcrest.MatcherAssert;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContext-test.xml"})
@Transactional
@Ignore
public class UserDaoImplTest extends TestCase {

    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        userDao = new UserDaoImpl(sessionFactory);
    }
}