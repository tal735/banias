package com.app.service.password;

import com.app.model.password.PasswordReset;

public interface PasswordService {
    String createResetToken(Long userId);
    PasswordReset getByUserIdAndToken(Long userId, String token);
    void deleteAllTokensForUser(Long userId);
    boolean resetPassword(Long userId, String password);
}
