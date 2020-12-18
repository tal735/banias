package com.app.service.password;

import com.app.model.password.PasswordReset;

public interface PasswordService {
    String createResetToken(Long userId);
    boolean resetPassword(String token, String password);
    PasswordReset getByToken(String token);
}
