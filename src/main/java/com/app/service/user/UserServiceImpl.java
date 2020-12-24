package com.app.service.user;

import com.app.dao.user.UserDao;
import com.app.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        email = email.toLowerCase().trim();
        return userDao.getByEmail(email);
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    @Transactional
    public User addNewUser(String email, String password) {
        User user = new User();
        user.setEmail(email.toLowerCase().trim());
        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);
        return user;
    }

    @Override
    @Transactional
    public User saveOrUpdate(User user) {
        userDao.save(user);
        return user;
    }


}
