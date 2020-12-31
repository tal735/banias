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
        deleteAllTokensForUser(userId);
        String token = RandomStringUtils.randomAlphanumeric(20);
        String tokenEncoded = passwordEncoder.encode(token);

        User user = userService.getById(userId);
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUser(user);
        passwordReset.setToken(tokenEncoded);
        passwordDao.save(passwordReset);
        return token;
    }

    @Override
    @Transactional
    public boolean resetPassword(Long userId, String password) {
        User user = userService.getById(userId);
        user.setPassword(passwordEncoder.encode(password));
        userService.saveOrUpdate(user);
        return true;
    }

    @Override
    @Transactional
    public PasswordReset getByUserIdAndToken(Long userId, String token) {
        PasswordReset passwordReset = passwordDao.getByUserId(userId);
        boolean match = passwordEncoder.matches(token, passwordReset.getToken());
        return match ? passwordReset : null;
    }

    @Override
    @Transactional
    public void deleteAllTokensForUser(Long userId) {
        passwordDao.deleteAllForUser(userId);
    }
}
