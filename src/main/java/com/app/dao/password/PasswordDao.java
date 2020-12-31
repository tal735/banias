package com.app.dao.password;

import com.app.model.password.PasswordReset;

public interface PasswordDao {
    void save(PasswordReset passwordReset);

    PasswordReset getByUserId(Long userId);

    void deleteAllForUser(Long userId);
}
