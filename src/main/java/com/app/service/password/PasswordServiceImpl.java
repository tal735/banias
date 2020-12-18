package com.app.service.password;

import com.app.dao.password.PasswordDao;
import com.app.model.password.PasswordReset;
import com.app.model.user.User;
import com.app.service.user.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordDao passwordDao;

    @Autowired
    public PasswordServiceImpl(UserService userService, PasswordEncoder passwordEncoder, PasswordDao passwordDao) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.passwordDao = passwordDao;
    }

    @Override
    @Transactional
    public String createResetToken(Long userId) {
        String token;
        String tokenEncoded;

        do {
            token = RandomStringUtils.randomAlphanumeric(50);
            tokenEncoded = passwordEncoder.encode(token);
        } while (passwordDao.getByToken(tokenEncoded) != null);

        User user = userService.getById(userId);
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUser(user);
        passwordReset.setToken(tokenEncoded);
        passwordDao.save(passwordReset);
        return token;
    }

    @Override
    @Transactional
    public boolean resetPassword(String token, String password) {
        PasswordReset passwordReset = passwordDao.getByToken(passwordEncoder.encode(token));
        passwordReset.setUsed(true);
        passwordDao.save(passwordReset);

        User user = passwordReset.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userService.saveOrUpdate(user);

        return true;
    }

    @Override
    @Transactional
    public PasswordReset getByToken(String token) {
        return passwordDao.getByToken(passwordEncoder.encode(token));
    }
}
