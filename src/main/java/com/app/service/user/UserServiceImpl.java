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
    public User saveOrUpdate(User user) {
        userDao.save(user);
        return user;
    }


}
