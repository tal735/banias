package com.app.service.user;

import com.app.dao.user.UserDao;
import com.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    @Transactional
    public synchronized User getOrCreateUser(String email) {
        User user = getByEmail(email);
        if (user == null) {
            user = createUser(email);
        }
        return user;
    }

    @Override
    @Transactional
    public synchronized User createInternalUser() {
        int count = userDao.getCount();
        String newEmail = String.format("user_%d@baniascamping.com", count);
        return createUser(newEmail);
    }

    private User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        userDao.save(user);
        return user;
    }
}
