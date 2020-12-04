package com.app.service.user;


import com.app.model.user.User;

public interface UserService {
    void save(User user);
    User getFirst();
}
