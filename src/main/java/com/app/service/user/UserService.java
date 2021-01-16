package com.app.service.user;

import com.app.model.user.User;

public interface UserService {
    User getByEmail(String email);
    User getById(Long id);
    User getOrCreateUser(String email);
    User createInternalUser();
}
